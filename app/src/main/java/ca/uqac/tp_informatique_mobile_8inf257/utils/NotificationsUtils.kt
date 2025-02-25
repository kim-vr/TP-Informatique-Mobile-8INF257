package ca.uqac.tp_informatique_mobile_8inf257.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import ca.uqac.tp_informatique_mobile_8inf257.presentation.NotificationVM

private val notificationsList = mutableStateListOf<NotificationVM>()


fun getReminders() : List<NotificationVM> {
    return notificationsList;
}

fun addNotification(notification: NotificationVM) {
    notificationsList.add(notification)
}

fun changeIsActiveInList(notification: NotificationVM) {
    val index = notificationsList.indexOfFirst { it.id == notification.id }
    if (index != -1) {
        //remindersList[index] = reminder.copy(active = !reminder.active)
    }
}

fun buttonBackgroundColor(notification: NotificationVM) : Color {
    if (notification.active.value) {
        return Color.Green
    }
    else {
        return Color.Gray
    }
}
