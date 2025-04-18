package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseQuizzes
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Answer
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Question
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuestionWithAnswers
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Quiz
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuizWithQuestionsAndAnswers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetQuizzesUseCaseTest {
    private lateinit var getQuizzesUseCase: GetQuizzesUseCase
    private lateinit var fakeDao: FakeDatabaseQuizzes

    @Before
    fun setup() {
        fakeDao = FakeDatabaseQuizzes()
        getQuizzesUseCase = GetQuizzesUseCase(fakeDao)
    }

    @Test
    fun `get empty quizzes list when none exist`() = runBlocking {
        // When
        val result = getQuizzesUseCase().first()

        // Then
        assertTrue("La liste devrait être vide", result.isEmpty())
    }

    @Test
    fun `get all quizzes with nested data`() = runBlocking {
        // Given
        val quiz1 = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Math Quiz"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(
                        id = 1,
                        quizId = 1,
                        text = "2 + 2",
                        correctAnswerIndex = 0
                    ),
                    answers = listOf(
                        Answer(id = 1, questionId = 1, text = "4", isCorrect = true),
                        Answer(id = 2, questionId = 1, text = "5", isCorrect = false)
                    )
                )
            )
        )

        val quiz2 = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 2, title = "Science Quiz"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(
                        id = 2,
                        quizId = 2,
                        text = "Element O",
                        correctAnswerIndex = 0
                    ),
                    answers = listOf(
                        Answer(id = 3, questionId = 2, text = "Oxygen", isCorrect = true)
                    )
                )
            )
        )

        fakeDao.upsertQuizWithQuestionsAndAnswers(quiz1)
        fakeDao.upsertQuizWithQuestionsAndAnswers(quiz2)

        // When
        val result = getQuizzesUseCase().first()

        // Then
        assertEquals("Deux quizzes attendus", 2, result.size)

        val mathQuiz = result.find { it.quiz.id == 1 }
        assertNotNull("Quiz de math manquant", mathQuiz)
        assertEquals(1, mathQuiz?.questions?.size)
        assertEquals(2, mathQuiz?.questions?.first()?.answers?.size)

        val scienceQuiz = result.find { it.quiz.id == 2 }
        assertNotNull("Quiz de science manquant", scienceQuiz)
        assertEquals(1, scienceQuiz?.questions?.size)
        assertEquals(1, scienceQuiz?.questions?.first()?.answers?.size)
    }

    @Test
    fun `quizzes should not share questions`() = runBlocking {
        // Given
        val quiz1 = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Quiz 1"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(
                        id = 1,
                        quizId = 1,
                        text = "Q1",
                        correctAnswerIndex = 0
                    ),
                    answers = emptyList()
                )
            )
        )

        val quiz2 = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 2, title = "Quiz 2"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(
                        id = 2,
                        quizId = 2,
                        text = "Q2",
                        correctAnswerIndex = 0
                    ),
                    answers = emptyList()
                )
            )
        )

        fakeDao.upsertQuizWithQuestionsAndAnswers(quiz1)
        fakeDao.upsertQuizWithQuestionsAndAnswers(quiz2)

        // When
        val result = getQuizzesUseCase().first()

        // Then
        result.forEach { quizWithData ->
            quizWithData.questions.forEach { questionWithAnswers ->
                assertEquals(
                    "Les questions doivent appartenir au bon quiz",
                    quizWithData.quiz.id,
                    questionWithAnswers.question.quizId
                )
            }
        }
    }

    @Test
    fun `handle quiz with no questions`() = runBlocking {
        // Given
        val quiz = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Empty Quiz"),
            questions = emptyList()
        )
        fakeDao.upsertQuizWithQuestionsAndAnswers(quiz)

        // When
        val result = getQuizzesUseCase().first()

        // Then
        assertEquals(1, result.size)
        assertTrue("Aucune question attendue", result[0].questions.isEmpty())
    }

    @Test
    fun `verify answers belong to correct questions`() = runBlocking {
        // Given
        val quiz = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Answer Test"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(
                        id = 1,
                        quizId = 1,
                        text = "Q1",
                        correctAnswerIndex = 0
                    ),
                    answers = listOf(
                        Answer(id = 1, questionId = 1, text = "A1", isCorrect = true)
                    )
                )
            )
        )
        fakeDao.upsertQuizWithQuestionsAndAnswers(quiz)

        // When
        val result = getQuizzesUseCase().first()

        // Then
        result.first().questions.first().answers.forEach { answer ->
            assertEquals(
                "Les réponses doivent appartenir à la question",
                1,
                answer.questionId
            )
        }
    }
}