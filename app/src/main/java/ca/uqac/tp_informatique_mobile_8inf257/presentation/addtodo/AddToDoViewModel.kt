package ca.uqac.tp_informatique_mobile_8inf257.presentation.addtodo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.uqac.tp_informatique_mobile_8inf257.navigation.Screen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ToDoVM
import ca.uqac.tp_informatique_mobile_8inf257.utils.addToDo

class AddToDoViewModel() : ViewModel(){
    private val _todo = mutableStateOf(ToDoVM())
    val todo : State<ToDoVM> = _todo

    fun onEvent(event : AddToDoEvent) {
        when (event) {
            is AddToDoEvent.EnteredDescription -> {
                _todo.value = todo.value.copy(description = event.description)
            }
            is AddToDoEvent.AddToDo -> {
                addToDo(todo.value)
            }
        }
    }
}