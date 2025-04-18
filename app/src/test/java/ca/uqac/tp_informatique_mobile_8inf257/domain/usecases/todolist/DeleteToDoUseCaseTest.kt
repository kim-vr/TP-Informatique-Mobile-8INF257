package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseToDoList
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteToDoUseCaseTest {
    private lateinit var fakeDao: FakeDatabaseToDoList
    private lateinit var deleteToDoUseCase: DeleteToDoUseCase

    @Before
    fun setUp() {
        fakeDao = FakeDatabaseToDoList()
        deleteToDoUseCase = DeleteToDoUseCase(fakeDao)
    }

    @Test
    fun `should delete a ToDo and ensure it no longer exists`() = runBlocking {
        // Setup: Create a ToDo and upsert it
        val newToDo = ToDo(
            id = 1,
            description = "Milk, eggs, and bread",
            done = false
        )
        fakeDao.upsertToDo(newToDo)

        // Act: Delete the ToDo
        deleteToDoUseCase(newToDo)

        // Assert: Ensure the ToDo no longer exists in the list
        val retrievedToDo = fakeDao.getToDo(1)
        assertNull(retrievedToDo)
    }

    @Test
    fun `should not delete a ToDo if it does not exist`() = runBlocking {
        // Act: Try to delete a ToDo that doesn't exist
        val nonExistentToDo = ToDo(
            id = 99,
            description = "This ToDo does not exist",
            done = false
        )

        // Try deleting it (this should not throw an error)
        deleteToDoUseCase(nonExistentToDo)

        // Assert: The ToDo list should be empty
        val toDoList = fakeDao.getToDoList().first()
        assertTrue(toDoList.isEmpty())
    }

    @Test
    fun `should not affect other ToDos when deleting one`() = runBlocking {
        // Setup: Add multiple ToDos
        val toDo1 = ToDo(
            id = 2,
            description = "Run for 30 minutes",
            done = false
        )
        val toDo2 = ToDo(
            id = 3,
            description = "Review math notes",
            done = true
        )
        fakeDao.upsertToDo(toDo1)
        fakeDao.upsertToDo(toDo2)

        // Act: Delete the first ToDo
        deleteToDoUseCase(toDo1)

        // Assert: Ensure the second ToDo still exists
        val toDoList = fakeDao.getToDoList().first()
        assertEquals(1, toDoList.size)
        assertTrue(toDoList.any { it.id == 3 })  // ToDo with ID 3 should remain
        assertFalse(toDoList.any { it.id == 2 }) // ToDo with ID 2 should be deleted
    }
}