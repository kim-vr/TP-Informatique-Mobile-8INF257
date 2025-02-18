package ca.uqac.tp_informatique_mobile_8inf257.utils

import ca.uqac.tp_informatique_mobile_8inf257.presentation.ToDoVM

private val toDoList: MutableList<ToDoVM> = mutableListOf()

fun getToDoList() : List<ToDoVM> {
    return toDoList;
}

fun addToDo(todo: ToDoVM) {
    toDoList.add(todo)
}

fun checkToDo(todo: ToDoVM){
    //TODO
}