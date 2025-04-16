package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist

import ca.uqac.tp_informatique_mobile_8inf257.data.source.ToDoListDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo
import ca.uqac.tp_informatique_mobile_8inf257.utils.ToDoException
import kotlin.jvm.Throws

class DeleteToDoUseCase(private val toDoListDao: ToDoListDao) {
    @Throws(ToDoException::class)
    suspend operator fun invoke(toDo: ToDo) {
        toDoListDao.deleteToDo(toDo)
    }
}