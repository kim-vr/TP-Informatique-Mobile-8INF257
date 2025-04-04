package ca.uqac.tp_informatique_mobile_8inf257.presentation

import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo
import kotlin.random.Random

data class ToDoVM(
    val id: Int = Random.nextInt(),
    val description: String = "",
    val done: Boolean = false
) {
    companion object {
        fun fromEntity(entity: ToDo): ToDoVM {
            return ToDoVM(
                id = entity.id!!,
                description = entity.description,
                done = entity.done
            )
        }
    }
}

fun ToDoVM.toEntity() : ToDo {
    val id = if (this.id == -1) null else this.id
    return ToDo(
        id = id,
        description = this.description,
        done = this.done
    )
}
