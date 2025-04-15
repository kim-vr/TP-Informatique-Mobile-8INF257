package ca.uqac.tp_informatique_mobile_8inf257.domain

import ca.uqac.tp_informatique_mobile_8inf257.data.source.NotificationsDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDatabaseNotifications : NotificationsDao {
    val notifications = mutableListOf<Notification>()

    override fun getNotifications(): Flow<List<Notification>> = flow {
        emit(notifications)
    }

    override fun getNotification(id: Int): Notification? {
        return notifications.find { it.id == id }
    }

    override suspend fun upsertNotification(notification: Notification) {
        notifications.removeIf { it.id == notification.id }
        notifications.add(notification)
    }

    override suspend fun deleteNotification(notification: Notification) {
        notifications.remove(notification)
    }
}