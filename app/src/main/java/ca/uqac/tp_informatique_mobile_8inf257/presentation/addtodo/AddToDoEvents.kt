package ca.uqac.tp_informatique_mobile_8inf257.presentation.addtodo

sealed interface AddToDoEvent {
    data class EnteredDescription(val description: String): AddToDoEvent
    data object AddToDo: AddToDoEvent
}