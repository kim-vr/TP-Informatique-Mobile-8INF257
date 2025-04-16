package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications

import ca.uqac.tp_informatique_mobile_8inf257.data.source.NotificationsDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification
import kotlinx.coroutines.flow.Flow

class GetNotificationsUseCase(private val notificationsDao: NotificationsDao) {
    operator fun invoke() : Flow<List<Notification>> {
        return notificationsDao.getNotifications()
    }
}