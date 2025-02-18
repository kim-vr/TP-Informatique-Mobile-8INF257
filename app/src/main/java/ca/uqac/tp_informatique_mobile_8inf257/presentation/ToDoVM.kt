package ca.uqac.tp_informatique_mobile_8inf257.presentation

import kotlin.random.Random

data class ToDoVM(
    val id: Int = Random.nextInt(),
    val description: String = "",
    val done: Boolean = false
)
