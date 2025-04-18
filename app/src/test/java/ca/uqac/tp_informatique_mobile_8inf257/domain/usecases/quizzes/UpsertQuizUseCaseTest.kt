package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseQuizzes
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Answer
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Question
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuestionWithAnswers
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Quiz
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuizWithQuestionsAndAnswers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UpsertQuizUseCaseTest {
    private lateinit var upsertUseCase: UpsertQuizUseCase
    private lateinit var fakeDao: FakeDatabaseQuizzes

    @Before
    fun setup() {
        fakeDao = FakeDatabaseQuizzes()
        upsertUseCase = UpsertQuizUseCase(fakeDao)
    }

    @Test
    fun `insert new quiz with questions and answers`() = runBlocking {
        // Given
        val newQuiz = QuizWithQuestionsAndAnswers(
            quiz = Quiz(title = "New Quiz"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(quizId = 0, text = "Q1", correctAnswerIndex = 0),
                    answers = listOf(
                        Answer(questionId = 0, text = "A1", isCorrect = true),
                        Answer(questionId = 1, text = "A2", isCorrect = false)
                    )
                )
            )
        )

        // When
        upsertUseCase(newQuiz)

        // Then
        val savedQuiz = fakeDao.getQuizWithQuestionsAndAnswers(1)!!
        assertEquals("New Quiz", savedQuiz.quiz.title)
        assertEquals(1, savedQuiz.questions.size)
        assertEquals(2, savedQuiz.questions[0].answers.size)

        // Verify IDs and relationships
        assertEquals(1, savedQuiz.quiz.id)
        assertEquals(1, savedQuiz.questions[0].question.quizId)
        assertEquals(1, savedQuiz.questions[0].answers[0].questionId)
    }

    @Test
    fun `update existing quiz with new data`() = runBlocking {
        // Given - Initial quiz
        val initialQuiz = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Initial Quiz"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(id = 1, quizId = 1, text = "Q1", correctAnswerIndex = 0),
                    answers = listOf(
                        Answer(id = 1, questionId = 1, text = "A1", isCorrect = true)
                    )
                )
            )
        )
        fakeDao.upsertQuizWithQuestionsAndAnswers(initialQuiz)

        // When - Update quiz
        val updatedQuiz = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Updated Quiz"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(id = 1, quizId = 1, text = "Updated Q1", correctAnswerIndex = 0),
                    answers = listOf(
                        Answer(id = 1, questionId = 1, text = "Updated A1", isCorrect = true),
                        Answer(questionId = 0, text = "New A2", isCorrect = false)
                    )
                )
            )
        )
        upsertUseCase(updatedQuiz)

        // Then
        val savedQuiz = fakeDao.getQuizWithQuestionsAndAnswers(1)!!
        assertEquals("Updated Quiz", savedQuiz.quiz.title)
        assertEquals("Updated Q1", savedQuiz.questions[0].question.text)
        assertEquals(2, savedQuiz.questions[0].answers.size)
        assertEquals("Updated A1", savedQuiz.questions[0].answers[0].text)
        assertEquals("New A2", savedQuiz.questions[0].answers[1].text)
    }

    @Test
    fun `upsert quiz without questions`() = runBlocking {
        // Given
        val quiz = QuizWithQuestionsAndAnswers(
            quiz = Quiz(title = "Empty Quiz"),
            questions = emptyList()
        )

        // When
        upsertUseCase(quiz)

        // Then
        val savedQuiz = fakeDao.getQuizWithQuestionsAndAnswers(1)!!
        assertEquals("Empty Quiz", savedQuiz.quiz.title)
        assertTrue(savedQuiz.questions.isEmpty())
    }

    @Test
    fun `add questions to existing quiz`() = runBlocking {
        // Given - Initial quiz without questions
        val initialQuiz = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Quiz"),
            questions = emptyList()
        )
        fakeDao.upsertQuizWithQuestionsAndAnswers(initialQuiz)

        // When - Add question
        val updatedQuiz = initialQuiz.copy(
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(quizId = 1, text = "New Q1", correctAnswerIndex = 0),
                    answers = listOf(
                        Answer(questionId = 0, text = "New A1", isCorrect = true)
                    )
                )
            )
        )
        upsertUseCase(updatedQuiz)

        // Then
        val savedQuiz = fakeDao.getQuizWithQuestionsAndAnswers(1)!!
        assertEquals(1, savedQuiz.questions.size)
        assertEquals("New Q1", savedQuiz.questions[0].question.text)
        assertEquals(1, savedQuiz.questions[0].answers.size)
    }

    @Test
    fun `maintain data isolation between quizzes`() = runBlocking {
        // Given - Two quizzes
        val quiz1 = QuizWithQuestionsAndAnswers(
            quiz = Quiz(title = "Quiz 1"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(quizId = 0, text = "Q1", correctAnswerIndex = 0),
                    answers = listOf(Answer(questionId = 0, text = "A1", isCorrect = true))
                )
            )
        )

        val quiz2 = QuizWithQuestionsAndAnswers(
            quiz = Quiz(title = "Quiz 2"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(quizId = 0, text = "Q2", correctAnswerIndex = 0),
                    answers = listOf(Answer(questionId = 0, text = "A2", isCorrect = true))
                )
            )
        )

        // When
        upsertUseCase(quiz1)
        upsertUseCase(quiz2)

        // Then
        val savedQuiz1 = fakeDao.getQuizWithQuestionsAndAnswers(1)!!
        val savedQuiz2 = fakeDao.getQuizWithQuestionsAndAnswers(2)!!

        assertEquals(1, savedQuiz1.questions[0].question.quizId)
        assertEquals(2, savedQuiz2.questions[0].question.quizId)
        assertNotEquals(
            savedQuiz1.questions[0].question.id,
            savedQuiz2.questions[0].question.id
        )
    }
}