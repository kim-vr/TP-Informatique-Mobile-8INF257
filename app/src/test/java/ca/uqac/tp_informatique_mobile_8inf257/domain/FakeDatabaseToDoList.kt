package ca.uqac.tp_informatique_mobile_8inf257.domain

import ca.uqac.tp_informatique_mobile_8inf257.data.source.ToDoListDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDatabaseToDoList : ToDoListDao {
    private val toDoList = mutableListOf<ToDo>()

    override fun getToDoList(): Flow<List<ToDo>> = flow {
        emit(toDoList)
    }

    override fun getToDo(id: Int): ToDo? {
        return toDoList.find { it.id == id }
    }

    override suspend fun upsertToDo(toDo: ToDo) {
        // Remove any existing ToDo with the same id
        toDoList.removeIf { it.id == toDo.id }
        // Add the new or updated ToDo
        toDoList.add(toDo)
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        toDoList.remove(toDo)
    }
}
