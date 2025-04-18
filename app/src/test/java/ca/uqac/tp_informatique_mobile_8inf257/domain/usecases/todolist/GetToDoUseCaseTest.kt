package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseToDoList
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist.GetToDoUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GetToDoUseCaseTest {
    private lateinit var fakeDao: FakeDatabaseToDoList
    private lateinit var getToDoUseCase: GetToDoUseCase

    @Before
    fun setUp() {
        fakeDao = FakeDatabaseToDoList()
        getToDoUseCase = GetToDoUseCase(fakeDao)
    }

    @Test
    fun `should return the correct ToDo by ID`() = runBlocking {
        // Setup: Add ToDo to the fake database
        val toDo = ToDo(
            id = 1,
            description = "Milk, eggs, and bread",
            done = false
        )
        fakeDao.upsertToDo(toDo)

        // Act: Get the ToDo by ID
        val fetchedToDo = getToDoUseCase(1)

        // Assert: The fetched ToDo should match the one we inserted
        assertNotNull(fetchedToDo)
        assertEquals(toDo.id, fetchedToDo?.id)
        assertEquals(toDo.description, fetchedToDo?.description)
    }

    @Test
    fun `should return null when ToDo does not exist`() = runBlocking {
        // Act: Try to get a ToDo with an ID that doesn't exist
        val fetchedToDo = getToDoUseCase(999)

        // Assert: The result should be null as the ToDo doesn't exist
        assertNull(fetchedToDo)
    }
}
