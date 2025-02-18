package ca.uqac.tp_informatique_mobile_8inf257.utils

import androidx.compose.ui.graphics.Color
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ReminderVM

private val remindersList: MutableList<ReminderVM> = mutableListOf(
)

fun getReminders() : List<ReminderVM> {
    return remindersList;
}

fun addReminder(reminder: ReminderVM) {
    remindersList.add(reminder)
}

fun changeIsActiveInList(reminder: ReminderVM) {
    val index = remindersList.indexOfFirst { it.id == reminder.id }
    if (index != -1) {
        //remindersList[index] = reminder.copy(active = !reminder.active)
    }
}

fun buttonBackgroundColor(reminder: ReminderVM) : Color {
    if (reminder.active.value) {
        return Color.Green
    }
    else {
        return Color.Gray
    }
}
