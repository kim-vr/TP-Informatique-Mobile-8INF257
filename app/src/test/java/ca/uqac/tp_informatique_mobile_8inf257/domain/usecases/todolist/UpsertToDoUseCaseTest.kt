package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseToDoList
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist.UpsertToDoUseCase
import ca.uqac.tp_informatique_mobile_8inf257.utils.ToDoException
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class UpsertToDoUseCaseTest {
    private lateinit var fakeDao: FakeDatabaseToDoList
    private lateinit var upsertToDoUseCase: UpsertToDoUseCase

    @Before
    fun setUp() {
        fakeDao = FakeDatabaseToDoList()
        upsertToDoUseCase = UpsertToDoUseCase(fakeDao)
    }

    @Test
    fun `should insert new ToDo when description is provided`() = runBlocking {
        // Given: A ToDo object with a valid description
        val toDo = ToDo(
            id = 1,
            description = "Milk, eggs, and bread",
            done = false
        )

        // Act: Call the use case to insert the ToDo
        upsertToDoUseCase(toDo)

        // Assert: The ToDo should be inserted into the fake database
        val insertedToDo = toDo.id?.let { fakeDao.getToDo(it) }
        assertNotNull(insertedToDo)
        assertEquals(toDo.description, insertedToDo?.description)
    }

    @Test(expected = ToDoException::class)
    fun `should throw exception when description is empty`() = runBlocking {
        // Given: A ToDo object with an empty description
        val toDo = ToDo(
            id = 2,
            description = "",
            done = false
        )

        // Act & Assert: The use case should throw a ToDoException
        upsertToDoUseCase(toDo)
    }

    @Test
    fun `should update existing ToDo when description is valid`() = runBlocking {
        // Given: A ToDo already inserted into the database
        val toDo = ToDo(
            id = 3,
            description = "Milk and bread",
            done = false
        )
        fakeDao.upsertToDo(toDo)

        // Given: A new ToDo object with the same ID but a different description
        val updatedToDo = ToDo(
            id = 3,
            description = "Milk, eggs, and bread",
            done = true
        )

        // Act: Call the use case to update the ToDo
        upsertToDoUseCase(updatedToDo)

        // Assert: The ToDo should be updated in the fake database
        val fetchedToDo = updatedToDo.id?.let { fakeDao.getToDo(it) }
        assertNotNull(fetchedToDo)
        assertEquals(updatedToDo.description, fetchedToDo?.description)
        assertEquals(updatedToDo.done, fetchedToDo?.done)
    }
}
