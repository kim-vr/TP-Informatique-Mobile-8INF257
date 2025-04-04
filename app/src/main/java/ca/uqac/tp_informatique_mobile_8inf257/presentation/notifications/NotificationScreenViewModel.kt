package ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.NotificationVM
import ca.uqac.tp_informatique_mobile_8inf257.utils.addNotification
import ca.uqac.tp_informatique_mobile_8inf257.utils.getReminders

class NotificationScreenViewModel() : ViewModel() {
    private val _notificationsList = mutableStateListOf<NotificationVM>().apply {
        addAll(getReminders()) // Charge les rappels au démarrage
    }
    val notificationsList: List<NotificationVM> get() = _notificationsList


    fun onEvent(event: NotificationsEvent) {
        when (event) {
            is NotificationsEvent.ChangeIsActive -> {
                // Mettre à jour l'état du rappel
                event.notification.active.value = !event.notification.active.value
            }

            is NotificationsEvent.Modify -> TODO()
            is NotificationsEvent.AddNotification -> {
                addNotification(event.notification)
                _notificationsList.add(event.notification)
            }
        }
    }

}