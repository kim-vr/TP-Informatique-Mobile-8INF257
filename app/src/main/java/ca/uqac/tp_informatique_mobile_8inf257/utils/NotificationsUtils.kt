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
