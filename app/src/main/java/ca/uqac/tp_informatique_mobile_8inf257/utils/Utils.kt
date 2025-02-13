package ca.uqac.tp_informatique_mobile_8inf257.utils

import androidx.compose.ui.graphics.Color
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ReminderVM

private val remindersList: MutableList<ReminderVM> = mutableListOf(
    ReminderVM(id = 1,
        "Finir TP mobile",
        selectedHour = "16:00",
        description = "A finir rapidement",
        days = "Mardi, Jeudi",
        timeLeft = "6h",
        active = false
    ),
    ReminderVM(id = 2,
        "Faire TP sécurité",
        selectedHour = "18:00",
        description = "TP donné le 10 février",
        days = "Lundi",
        timeLeft = "8h",
        active = true
    )
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
        remindersList[index] = reminder.copy(active = !reminder.active)
    }
}

fun buttonBackgroundColor(reminder: ReminderVM) : Color {
    if (reminder.active) {
        return Color.Green
    }
    else {
        return Color.Gray
    }
}
