package ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications

import ca.uqac.tp_informatique_mobile_8inf257.presentation.ReminderVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.listreminders.ReminderEvent

sealed class NotificationsEvent {
    data class Modify(val reminder: ReminderVM) : NotificationsEvent()
    data class ChangeIsActive(val reminder: ReminderVM) : NotificationsEvent()
}