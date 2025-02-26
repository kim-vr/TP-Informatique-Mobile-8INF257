package ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ToDoVM
import ca.uqac.tp_informatique_mobile_8inf257.utils.getCheckedToDoList
import ca.uqac.tp_informatique_mobile_8inf257.utils.getToDoList
import ca.uqac.tp_informatique_mobile_8inf257.utils.getUncheckedToDoList
import ca.uqac.tp_informatique_mobile_8inf257.utils.updateToDoStatus

class ToDoListViewModel() : ViewModel() {
    private val _todos: MutableState<List<ToDoVM>> = mutableStateOf(emptyList())
    var todos: State<List<ToDoVM>> = _todos
    private val _checkedTodos: MutableState<List<ToDoVM>> = mutableStateOf(emptyList())
    var checkedTodos: State<List<ToDoVM>> = _checkedTodos
    private val _uncheckedTodos: MutableState<List<ToDoVM>> = mutableStateOf(emptyList())
    var uncheckedTodos: State<List<ToDoVM>> = _uncheckedTodos

    init {
        _todos.value = loadToDos()
        _uncheckedTodos.value = loadUncheckedTodos()
        _checkedTodos.value = loadCheckedTodos()
    }

    private fun loadToDos(): List<ToDoVM> {
        return getToDoList()
    }

    private fun loadUncheckedTodos(): List<ToDoVM> {
        return getUncheckedToDoList()
    }

    private fun loadCheckedTodos(): List<ToDoVM> {
        return getCheckedToDoList()
    }

    fun onEvent(event: ToDoEvent) {
        when(event) {
            is ToDoEvent.Check -> {
                checkToDo(event.todo)
            }
        }
    }

    private fun checkToDo(todo: ToDoVM) {
        // Met à jour la liste locale du ViewModel
        _todos.value = _todos.value.map {
            if (it == todo) it.copy(done = !it.done) else it
        }

        // Met à jour la liste globale dans utils
        updateToDoStatus(todo)

        // Met à jour les listes affichées
        _uncheckedTodos.value = _todos.value.filter { !it.done }
        _checkedTodos.value = _todos.value.filter { it.done }
    }

}