package ca.uqac.tp_informatique_mobile_8inf257.presentation.addtodo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist.ToDoListUseCases
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ToDoVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddToDoViewModel @Inject constructor
    (private val toDoListUseCases: ToDoListUseCases) : ViewModel(){
    private val _todo = mutableStateOf(ToDoVM())
    val todo : State<ToDoVM> = _todo

    fun onEvent(event : AddToDoEvent) {
        when (event) {
            is AddToDoEvent.EnteredDescription -> {
                _todo.value = todo.value.copy(description = event.description)
            }
            is AddToDoEvent.AddToDo -> {
                viewModelScope.launch {
                    if(todo.value.description.isEmpty()) {
                        // TODO
                    } else {
                        val entity = todo.value.toEntity()
                        toDoListUseCases.upsertToDo(entity)
                        // TODO
                    }
                }
            }
        }
    }
}