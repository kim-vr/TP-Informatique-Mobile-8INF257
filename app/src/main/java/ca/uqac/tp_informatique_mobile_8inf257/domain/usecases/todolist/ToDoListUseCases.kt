package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist

data class ToDoListUseCases (
    val getToDoList : GetToDoListUseCase,
    val getToDo : GetToDoUseCase,
    val upsertToDo : UpsertToDoUseCase,
    val deleteToDo : DeleteToDoUseCase
)