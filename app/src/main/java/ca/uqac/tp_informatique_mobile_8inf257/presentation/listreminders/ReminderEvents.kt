package ca.uqac.tp_informatique_mobile_8inf257.presentation.listreminders

import ca.uqac.tp_informatique_mobile_8inf257.presentation.ReminderVM

sealed class ReminderEvent {
    data class Modify(val reminder: ReminderVM) : ReminderEvent()
    data class ChangeIsActive(val reminder: ReminderVM) : ReminderEvent()
}