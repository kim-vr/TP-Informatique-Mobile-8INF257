package ca.uqac.tp_informatique_mobile_8inf257.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification
import kotlin.random.Random

data class NotificationVM(
    val id: Int = -1,
    val title: String = "",
    val selectedHour: String = "00:00",
    val description: String = "",
    val days: String = "Une fois",
    val timeLeft: String = "",
    val active: MutableState<Boolean> = mutableStateOf(false)
) {
    companion object {
        fun fromEntity(entity: Notification): NotificationVM {
            return NotificationVM(
                id = entity.id ?: -1,
                title = entity.title,
                selectedHour = entity.selectedHour,
                description = entity.description,
                days = entity.days,
                timeLeft = entity.timeLeft,
                active = mutableStateOf(entity.active)
            )
        }
    }
}

fun NotificationVM.toEntity(): Notification {
    return Notification(
        id = if (this.id == -1) null else this.id,
        title = this.title,
        selectedHour = this.selectedHour,
        description = this.description,
        days = this.days,
        timeLeft = this.timeLeft,
        active = this.active.value
    )
}