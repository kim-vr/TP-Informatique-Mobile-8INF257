package ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist

import ca.uqac.tp_informatique_mobile_8inf257.presentation.ToDoVM

sealed class ToDoEvent {
    data class Check(val todo: ToDoVM) : ToDoEvent()
}