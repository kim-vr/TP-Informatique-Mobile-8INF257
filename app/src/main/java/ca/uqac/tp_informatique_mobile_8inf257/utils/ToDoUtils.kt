package ca.uqac.tp_informatique_mobile_8inf257.utils

import ca.uqac.tp_informatique_mobile_8inf257.presentation.ToDoVM

private val toDoList: MutableList<ToDoVM> = mutableListOf()


fun getToDoList() : List<ToDoVM> {
    return toDoList;
}

fun getCheckedToDoList(): List<ToDoVM> {
    return toDoList.filter { it.done }
}

fun getUncheckedToDoList(): List<ToDoVM> {
    return toDoList.filter { !it.done }
}
fun updateToDoStatus(todo: ToDoVM) {
    val index = toDoList.indexOfFirst { it.id == todo.id }
    if (index != -1) {
        toDoList[index] = toDoList[index].copy(done = !toDoList[index].done)
    }
}

class ToDoException(message: String) : Throwable(message)



fun addToDo(todo: ToDoVM) {
    toDoList.add(todo)
}