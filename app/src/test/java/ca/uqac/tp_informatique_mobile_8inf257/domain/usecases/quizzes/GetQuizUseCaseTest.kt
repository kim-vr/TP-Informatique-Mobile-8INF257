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

class GetQuizUseCaseTest {
    private lateinit var getQuizUseCase: GetQuizUseCase
    private lateinit var fakeDao: FakeDatabaseQuizzes

    @Before
    fun setup() {
        fakeDao = FakeDatabaseQuizzes()
        getQuizUseCase = GetQuizUseCase(fakeDao)
    }

    @Test
    fun `get existing quiz with full structure`() = runBlocking {
        // Given: Un quiz complet dans la base
        val expectedQuiz = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Math Quiz"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(id = 1, quizId = 1, text = "2 + 2", correctAnswerIndex = 0),
                    answers = listOf(
                        Answer(id = 1, questionId = 1, text = "4", isCorrect = true),
                        Answer(id = 2, questionId = 1, text = "5", isCorrect = false)
                    )
                )
            )
        )
        fakeDao.upsertQuizWithQuestionsAndAnswers(expectedQuiz)

        // When
        val result = getQuizUseCase(1)

        // Then
        assertNotNull("Le quiz devrait être trouvé", result)
        assertEquals("Titre incorrect", "Math Quiz", result?.quiz?.title)
        assertEquals("Nombre de questions incorrect", 1, result?.questions?.size)
        assertEquals("Nombre de réponses incorrect", 2, result?.questions?.first()?.answers?.size)
    }

    @Test
    fun `get non-existent quiz should return null`() = runBlocking {
        // When
        val result = getQuizUseCase(999)

        // Then
        assertNull("Aucun quiz ne devrait être trouvé", result)
    }

    @Test
    fun `get quiz should not return other quizzes data`() = runBlocking {
        // Given: Deux quizzes distincts
        val quiz1 = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Quiz 1"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(
                        id = 1,
                        quizId = 1,
                        text = "Q1",
                        correctAnswerIndex = 0 // Ajouté ici
                    ),
                    answers = listOf(
                        Answer(id = 1, questionId = 1, text = "A1", isCorrect = true)
                    )
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
                        correctAnswerIndex = 0 // Ajouté ici
                    ),
                    answers = listOf(
                        Answer(id = 2, questionId = 2, text = "A2", isCorrect = true)
                    )
                )
            )
        )

        fakeDao.upsertQuizWithQuestionsAndAnswers(quiz1)
        fakeDao.upsertQuizWithQuestionsAndAnswers(quiz2)

        // When
        val result = getQuizUseCase(1)

        // Then
        assertEquals("Mauvais quiz récupéré", 1, result?.quiz?.id)
        assertEquals("Mauvaise question récupérée", 1, result?.questions?.first()?.question?.id)
        assertEquals("Mauvaise réponse récupérée", 1, result?.questions?.first()?.answers?.first()?.id)
    }

    @Test
    fun `get quiz with no questions should return empty list`() = runBlocking {
        // Given
        val quiz = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Empty Quiz"),
            questions = emptyList()
        )
        fakeDao.upsertQuizWithQuestionsAndAnswers(quiz)

        // When
        val result = getQuizUseCase(1)

        // Then
        assertTrue("Aucune question ne devrait être présente", result?.questions?.isEmpty() == true)
    }

    @Test
    fun `get quiz with multiple questions`() = runBlocking {
        // Given
        val quiz = QuizWithQuestionsAndAnswers(
            quiz = Quiz(id = 1, title = "Multi-Question Quiz"),
            questions = listOf(
                QuestionWithAnswers(
                    question = Question(
                        id = 1,
                        quizId = 1,
                        text = "Q1",
                        correctAnswerIndex = 0
                    ),
                    answers = emptyList()
                ),
                QuestionWithAnswers(
                    question = Question(
                        id = 2,
                        quizId = 1,
                        text = "Q2",
                        correctAnswerIndex = 0
                    ),
                    answers = emptyList()
                )
            )
        )
        fakeDao.upsertQuizWithQuestionsAndAnswers(quiz)

        // When
        val result = getQuizUseCase(1)

        // Then
        assertEquals("Deux questions attendues", 2, result?.questions?.size)
    }
}