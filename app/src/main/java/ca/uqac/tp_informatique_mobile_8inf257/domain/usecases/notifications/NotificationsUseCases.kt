package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications

data class NotificationsUseCases (
    val getNotifications : GetNotificationsUseCase,
    val getNotification : GetNotificationUseCase,
    val upsertNotification : UpsertNotificationUseCase,
    val deleteNotification : DeleteNotificationUseCase
)
