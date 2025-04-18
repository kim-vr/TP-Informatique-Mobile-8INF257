package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist

import ca.uqac.tp_informatique_mobile_8inf257.data.source.ToDoListDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo
import ca.uqac.tp_informatique_mobile_8inf257.utils.ToDoException
import kotlin.jvm.Throws

class UpsertToDoUseCase(private val toDoListDao: ToDoListDao) {
    @Throws(ToDoException::class)
    suspend operator fun invoke(toDo: ToDo) {
        if (toDo.description.isEmpty())
            throw ToDoException("La description ne doit pas être vide")
        toDoListDao.upsertToDo(toDo)
    }
}