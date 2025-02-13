package ca.uqac.tp_informatique_mobile_8inf257.utils

import ca.uqac.tp_informatique_mobile_8inf257.presentation.ReminderVM

private val remindersList: MutableList<ReminderVM> = mutableListOf(
    ReminderVM(id = 1,
        "Finir TP mobile",
        selectedHour = "16:00",
        description = "A finir rapidement",
        days = "Mardi, Jeudi",
        timeLeft = "6h"
    ),
    ReminderVM(id = 2,
        "Faire TP sécurité",
        selectedHour = "18:00",
        description = "TP donné le 10 février",
        days = "Lundi",
        timeLeft = "8h"
    )
)

fun getReminders() : List<ReminderVM> {
    return remindersList;
}

fun addReminder(reminder: ReminderVM) {

    remindersList.add(reminder)
}