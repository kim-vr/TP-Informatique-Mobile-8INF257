package ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ToDoVM
import ca.uqac.tp_informatique_mobile_8inf257.utils.checkToDo
import ca.uqac.tp_informatique_mobile_8inf257.utils.getToDoList

class ToDoListViewModel() : ViewModel() {
    private val _todos: MutableState<List<ToDoVM>> = mutableStateOf(emptyList())
    var todos: State<List<ToDoVM>> = _todos

    init {
        _todos.value = loadToDos()
    }

    private fun loadToDos(): List<ToDoVM> {
        return getToDoList()
    }

    fun onEvent(event: ToDoEvent) {
        when(event) {
            is ToDoEvent.Check -> {
                checkToDo(event.todo)
            }
        }
    }

    private fun checkToDo(todo: ToDoVM) {
        _todos.value = _todos.value.map{
            if (it == todo) it.copy(done = !it.done) else it
        }
    }
}