package ca.uqac.tp_informatique_mobile_8inf257.data.source

import androidx.room.*
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizzesDao {

    // --- Quiz ---
    @Query("SELECT * FROM quiz")
    fun getQuizzes(): Flow<List<Quiz>>

    @Query("SELECT * FROM quiz WHERE id = :id")
    suspend fun getQuiz(id: Int): Quiz?

    @Upsert
    suspend fun upsertQuiz(quiz: Quiz)

    @Delete
    suspend fun deleteQuiz(quiz: Quiz)

    @Query("SELECT id FROM quiz ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedQuizId(): Int

    // --- Question ---
    @Query("SELECT * FROM questions WHERE quizId = :quizId")
    fun getQuestionsForQuiz(quizId: Int): Flow<List<Question>>

    @Upsert
    suspend fun upsertQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("SELECT id FROM questions ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedQuestionId(): Int

    // --- Answer ---
    @Query("SELECT * FROM answers WHERE questionId = :questionId")
    fun getAnswersForQuestion(questionId: Int): Flow<List<Answer>>

    @Upsert
    suspend fun upsertAnswer(answer: Answer)

    @Delete
    suspend fun deleteAnswer(answer: Answer)

    // --- Relations imbriquées ---
    @Transaction
    @Query("SELECT * FROM quiz WHERE id = :quizId")
    suspend fun getFullQuiz(quizId: Int): QuizWithQuestionsAndAnswers

    @Transaction
    suspend fun upsertQuizWithQuestionsAndAnswers(data: QuizWithQuestionsAndAnswers) {
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


    @Transaction
    @Query("SELECT * FROM quiz WHERE id = :quizId")
    suspend fun getQuizWithQuestionsAndAnswers(quizId: Int): QuizWithQuestionsAndAnswers?


    @Transaction
    @Query("SELECT * FROM quiz")
    fun getQuizzesWithQuestionsAndAnswers(): Flow<List<QuizWithQuestionsAndAnswers>>


}
