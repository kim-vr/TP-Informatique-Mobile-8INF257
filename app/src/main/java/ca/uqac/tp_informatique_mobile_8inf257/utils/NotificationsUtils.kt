package ca.uqac.tp_informatique_mobile_8inf257.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import ca.uqac.tp_informatique_mobile_8inf257.presentation.NotificationVM
import kotlinx.coroutines.flow.flow

private val notificationsList = mutableStateListOf<NotificationVM>()


fun getReminders() = flow {
    emit(notificationsList)
}

fun addNotification(notification: NotificationVM) {
    notificationsList.add(notification)
}

class NotificationException(message: String) : Throwable(message)