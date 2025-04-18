package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseToDoList
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetToDoListUseCaseTest {
    private lateinit var fakeDao: FakeDatabaseToDoList
    private lateinit var getToDoListUseCase: GetToDoListUseCase

    @Before
    fun setUp() {
        fakeDao = FakeDatabaseToDoList()
        getToDoListUseCase = GetToDoListUseCase(fakeDao)
    }

    @Test
    fun `should return the list of all ToDos`() = runBlocking {
        // Setup: Add ToDos to the fake database
        val toDo1 = ToDo(
            id = 1,
            description = "Milk, eggs, and bread",
            done = false
        )
        val toDo2 = ToDo(
            id = 2,
            description = "Run for 30 minutes",
            done = true
        )
        fakeDao.upsertToDo(toDo1)
        fakeDao.upsertToDo(toDo2)

        // Act: Get the ToDo list
        val toDoList = getToDoListUseCase().first()

        // Assert: The list should contain both ToDos
        assertEquals(2, toDoList.size)
        assertTrue(toDoList.any { it.id == 1 })
        assertTrue(toDoList.any { it.id == 2 })
    }

    @Test
    fun `should return an empty list when no ToDos exist`() = runBlocking {
        // Act: Get the ToDo list from an empty database
        val toDoList = getToDoListUseCase().first()

        // Assert: The list should be empty
        assertTrue(toDoList.isEmpty())
    }

    @Test
    fun `should return the correct list when some ToDos are deleted`() = runBlocking {
        // Setup: Add ToDos to the fake database
        val toDo1 = ToDo(
            id = 1,
            description = "Milk, eggs, and bread",
            done = false
        )
        val toDo2 = ToDo(
            id = 2,
            description = "Run for 30 minutes",
            done = true
        )
        fakeDao.upsertToDo(toDo1)
        fakeDao.upsertToDo(toDo2)

        // Act: Delete the first ToDo
        fakeDao.deleteToDo(toDo1)

    }
}