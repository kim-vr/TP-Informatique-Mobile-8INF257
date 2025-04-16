package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist

import ca.uqac.tp_informatique_mobile_8inf257.data.source.ToDoListDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

class GetToDoListUseCase(private val toDoListDao: ToDoListDao) {
    operator fun invoke() : Flow<List<ToDo>> {
        return toDoListDao.getToDoList()
    }
}