package ca.uqac.tp_informatique_mobile_8inf257.domain

import ca.uqac.tp_informatique_mobile_8inf257.data.source.QuizzesDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Answer
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Question
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuestionWithAnswers
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Quiz
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuizWithQuestionsAndAnswers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDatabaseQuizzes : QuizzesDao {

    private val quizzes = mutableListOf<Quiz>()
    private val questions = mutableListOf<Question>()
    private val answers = mutableListOf<Answer>()

    // --- Quiz ---
    override fun getQuizzes(): Flow<List<Quiz>> {
        return flow { emit(quizzes) }
    }

    override suspend fun getQuiz(id: Int): Quiz? {
        return quizzes.find { it.id == id }
    }

    override suspend fun upsertQuiz(quiz: Quiz) {
        if (quiz.id == 0) {
            val newId = quizzes.size + 1
            quizzes.add(quiz.copy(id = newId))
        } else {
            val existingIndex = quizzes.indexOfFirst { it.id == quiz.id }
            if (existingIndex != -1) {
                quizzes[existingIndex] = quiz
            } else {
                quizzes.add(quiz)
            }
        }
    }

    override suspend fun deleteQuiz(quiz: Quiz) {
        quizzes.removeIf { it.id == quiz.id }
    }

    override suspend fun getLastInsertedQuizId(): Int {
        return quizzes.lastOrNull()?.id ?: 0
    }

    // --- Question ---
    override fun getQuestionsForQuiz(quizId: Int): Flow<List<Question>> {
        return flow { emit(questions.filter { it.quizId == quizId }) }
    }

    override suspend fun upsertQuestion(question: Question) {
        if (question.id == 0) {
            val newId = questions.size + 1
            questions.add(question.copy(id = newId))
        } else {
            val existingIndex = questions.indexOfFirst { it.id == question.id }
            if (existingIndex != -1) {
                questions[existingIndex] = question
            } else {
                questions.add(question)
            }
        }
    }

    override suspend fun deleteQuestion(question: Question) {
        questions.removeIf { it.id == question.id }
    }

    override suspend fun getLastInsertedQuestionId(): Int {
        return questions.lastOrNull()?.id ?: 0
    }

    // --- Answer ---
    override fun getAnswersForQuestion(questionId: Int): Flow<List<Answer>> {
        return flow { emit(answers.filter { it.questionId == questionId }) }
    }

    override suspend fun upsertAnswer(answer: Answer) {
        if (answer.id == 0) {
            val newId = answers.size + 1
            answers.add(answer.copy(id = newId))
        } else {
            val existingIndex = answers.indexOfFirst { it.id == answer.id }
            if (existingIndex != -1) {
                answers[existingIndex] = answer
            } else {
                answers.add(answer)
            }
        }
    }

    override suspend fun deleteAnswer(answer: Answer) {
        answers.removeIf { it.id == answer.id }
    }

    // --- Relations imbriquées ---
    override suspend fun getFullQuiz(quizId: Int): QuizWithQuestionsAndAnswers {
        val quiz = getQuiz(quizId) ?: throw IllegalArgumentException("Quiz not found")
        val quizQuestions = questions.filter { it.quizId == quizId }
        val questionWithAnswers = quizQuestions.map { question ->
            QuestionWithAnswers(
                question = question,
                answers = answers.filter { it.questionId == question.id }
            )
        }
        return QuizWithQuestionsAndAnswers(quiz = quiz, questions = questionWithAnswers)
    }

    override suspend fun upsertQuizWithQuestionsAndAnswers(data: QuizWithQuestionsAndAnswers) {
        val quizId = if (data.quiz.id == 0) {
            upsertQuiz(data.quiz)
            getLastInsertedQuizId()
        } else {
            upsertQuiz(data.quiz)
            data.quiz.id
        }

        for (qwa in data.questions) {
            val question = qwa.question.copy(quizId = quizId)
            upsertQuestion(question)

            val questionId = if (question.id == 0) {
                getLastInsertedQuestionId()
            } else {
                question.id
            }

            for (answer in qwa.answers) {
                upsertAnswer(answer.copy(questionId = questionId))
            }
        }
    }

    override suspend fun getQuizWithQuestionsAndAnswers(quizId: Int): QuizWithQuestionsAndAnswers? {
        val quiz = getQuiz(quizId) ?: return null
        val questionsForQuiz = questions.filter { it.quizId == quizId }
        val questionWithAnswers = questionsForQuiz.map { question ->
            QuestionWithAnswers(
                question = question,
                answers = answers.filter { it.questionId == question.id }
            )
        }
        return QuizWithQuestionsAndAnswers(quiz = quiz, questions = questionWithAnswers)
    }

    override fun getQuizzesWithQuestionsAndAnswers(): Flow<List<QuizWithQuestionsAndAnswers>> {
        return flow {
            emit(
                quizzes.map { quiz ->
                    val questionsForQuiz = questions.filter { it.quizId == quiz.id }
                    val questionWithAnswers = questionsForQuiz.map { question ->
                        QuestionWithAnswers(
                            question = question,
                            answers = answers.filter { it.questionId == question.id }
                        )
                    }
                    QuizWithQuestionsAndAnswers(quiz = quiz, questions = questionWithAnswers)
                }
            )
        }
    }

}