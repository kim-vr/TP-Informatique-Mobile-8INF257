package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases

import ca.uqac.tp_informatique_mobile_8inf257.data.source.NotificationsDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification

class GetNotificationUseCase(private val notificationsDao: NotificationsDao) {
    operator fun invoke(notificationId: Int) : Notification? {
        return notificationsDao.getNotification(notificationId)
    }
}