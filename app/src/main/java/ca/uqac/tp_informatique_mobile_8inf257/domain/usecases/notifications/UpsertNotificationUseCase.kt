package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications

import ca.uqac.tp_informatique_mobile_8inf257.data.source.NotificationsDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification
import ca.uqac.tp_informatique_mobile_8inf257.utils.NotificationException
import kotlin.jvm.Throws

class UpsertNotificationUseCase(private val notificationsDao: NotificationsDao) {
    @Throws(NotificationException::class)
    suspend operator fun invoke(notification: Notification) {
        if (notification.title.isEmpty() || notification.description.isEmpty())
            throw NotificationException("Tous les champs obligatoires ne sont pas remplis")
        notificationsDao.upsertNotification(notification)
    }

}