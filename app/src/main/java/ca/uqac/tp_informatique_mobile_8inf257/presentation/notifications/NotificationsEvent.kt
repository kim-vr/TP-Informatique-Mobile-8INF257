package ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications

import ca.uqac.tp_informatique_mobile_8inf257.presentation.NotificationVM

sealed class NotificationsEvent {
    data class Modify(val notification: NotificationVM) : NotificationsEvent()
    data class ChangeIsActive(val notification: NotificationVM) : NotificationsEvent()
    data class AddNotification(val notification: NotificationVM) : NotificationsEvent()
}