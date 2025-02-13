package ca.uqac.tp_informatique_mobile_8inf257.presentation.listreminders

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ReminderVM
import ca.uqac.tp_informatique_mobile_8inf257.utils.changeIsActiveInList
import ca.uqac.tp_informatique_mobile_8inf257.utils.getReminders

class ListRemindersViewModel() : ViewModel() {
    private val _reminders: MutableState<List<ReminderVM>> = mutableStateOf(emptyList())
    var reminders: State<List<ReminderVM>> = _reminders

    init {
        _reminders.value = loadReminders()
    }

    private fun loadReminders() : List<ReminderVM> {
        return getReminders()
    }

    fun onEvent(event: ReminderEvent) {
        when(event) {
            is ReminderEvent.ChangeIsActive -> {
                changeIsActive(event.reminder)
            }
            is ReminderEvent.Modify -> TODO()
        }
    }

    private fun changeIsActive(reminder: ReminderVM) {
        _reminders.value = _reminders.value.toMutableList().apply {
            val index = indexOfFirst { it.id == reminder.id }
            if (index != -1) {
                this[index] = this[index].copy(active = !this[index].active)
            }
        }
        changeIsActiveInList(reminder)
    }
}