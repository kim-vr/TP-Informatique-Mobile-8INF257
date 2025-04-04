package ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.ToDoListUseCases
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ToDoVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.toEntity
import ca.uqac.tp_informatique_mobile_8inf257.utils.getCheckedToDoList
import ca.uqac.tp_informatique_mobile_8inf257.utils.getToDoList
import ca.uqac.tp_informatique_mobile_8inf257.utils.getUncheckedToDoList
import ca.uqac.tp_informatique_mobile_8inf257.utils.updateToDoStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor
    (private val toDoListUseCases: ToDoListUseCases) : ViewModel() {
    private val _todos: MutableState<List<ToDoVM>> = mutableStateOf(emptyList())
    var todos: State<List<ToDoVM>> = _todos

    private val _checkedTodos: MutableState<List<ToDoVM>> = mutableStateOf(emptyList())
    var checkedTodos: State<List<ToDoVM>> = _checkedTodos

    private val _uncheckedTodos: MutableState<List<ToDoVM>> = mutableStateOf(emptyList())
    var uncheckedTodos: State<List<ToDoVM>> = _uncheckedTodos

    var job: Job? = null

    init {
        loadToDos()
    }

    private fun loadToDos() {
        job?.cancel()

        job = toDoListUseCases.getToDoList().onEach { toDoList ->
            _todos.value = toDoList.map {
                ToDoVM.fromEntity(it)
            }
            _uncheckedTodos.value = _todos.value.filter { !it.done }
            _checkedTodos.value = _todos.value.filter { it.done }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ToDoEvent) {
        when(event) {
            is ToDoEvent.Check -> {
                checkToDo(event.todo)
            }
        }
    }

    private fun checkToDo(todo: ToDoVM) {
        viewModelScope.launch {
            val updatedTodo = todo.copy(done = !todo.done)
            toDoListUseCases.upsertToDo(updatedTodo.toEntity())
        }
    }

}