package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseQuizzes
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Quiz
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteQuizUseCaseTest {
    private lateinit var deleteQuizUseCase: DeleteQuizUseCase
    private lateinit var fakeDao: FakeDatabaseQuizzes

    @Before
    fun setUp() {
        fakeDao = FakeDatabaseQuizzes()
        deleteQuizUseCase = DeleteQuizUseCase(fakeDao)

        runBlocking {
            // Crée un quiz avec ID = 1 explicitement
            fakeDao.upsertQuiz(Quiz(id = 1, title = "Quiz initial"))
        }
    }

    @Test
    fun `delete existing quiz should remove from database`() = runBlocking {
        // Given
        val initialCount = fakeDao.getQuizzes().first().size // Devrait être 1

        // When
        deleteQuizUseCase(Quiz(id = 1, title = "Quiz initial"))

        // Then
        val quizzes = fakeDao.getQuizzes().first()
        assertEquals("La taille devrait diminuer de 1", initialCount - 1, quizzes.size)
        assertNull("Le quiz ne devrait plus exister", fakeDao.getQuiz(1))
    }

    @Test
    fun `deleting one quiz should not affect others`() = runBlocking {
        // Given
        fakeDao.upsertQuiz(Quiz(id = 2, title = "Second quiz"))
        val initialCount = fakeDao.getQuizzes().first().size // Devrait être 2

        // When
        deleteQuizUseCase(Quiz(id = 1, title = "Quiz initial"))

        // Then
        val quizzes = fakeDao.getQuizzes().first()
        assertEquals("Un seul quiz devrait être supprimé", initialCount - 1, quizzes.size)
        assertNotNull("Le deuxième quiz devrait rester", fakeDao.getQuiz(2))
    }
}