package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases

import ca.uqac.tp_informatique_mobile_8inf257.data.source.ToDoListDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo

class GetToDoUseCase(private val toDoListDao: ToDoListDao) {
    operator fun invoke(toDoId: Int) : ToDo?{
        return toDoListDao.getToDo(toDoId)
    }
}