package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseNotifications
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetNotificationUseCaseTest {
    private lateinit var getNotificationUseCase: GetNotificationUseCase
    private val fakeDao = FakeDatabaseNotifications()

    @Before
    fun setUp() {
        getNotificationUseCase = GetNotificationUseCase(fakeDao)

        // Pré-remplir avec des notifications
        fakeDao.notifications.addAll(
            listOf(
                Notification(
                    id = 1,
                    title = "Alerte Matin",
                    selectedHour = "07:30",
                    description = "Réveil",
                    days = "Lundi",
                    timeLeft = "30",
                    active = true
                ),
                Notification(
                    id = 2,
                    title = "Pause café",
                    selectedHour = "10:00",
                    description = "Petite pause",
                    days = "Mardi",
                    timeLeft = "15",
                    active = false
                )
            )
        )
    }

    @Test
    fun `should return notification with given ID`() {
        val notification = getNotificationUseCase(1)
        assertNotNull(notification)
        assertEquals("Alerte Matin", notification?.title)
    }

    @Test
    fun `should return null if notification ID does not exist`() {
        val notification = getNotificationUseCase(999)
        assertNull(notification)
    }
}