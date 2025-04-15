package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseNotifications
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification
import ca.uqac.tp_informatique_mobile_8inf257.utils.NotificationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UpsertNotificationUseCaseTest {
    private lateinit var upsertNotificationUseCase: UpsertNotificationUseCase
    private val fakeDao = FakeDatabaseNotifications()

    @Before
    fun setUp() {
        upsertNotificationUseCase = UpsertNotificationUseCase(fakeDao)
    }

    @Test
    fun `notification should be added if fields are valid`() = runBlocking {
        val notification = Notification(
            id = 1,
            title = "Nouvelle alerte",
            selectedHour = "08:30",
            description = "Test description",
            days = "Lundi",
            timeLeft = "30",
            active = true
        )
        upsertNotificationUseCase(notification)

        val notifications = fakeDao.getNotifications().first()
        assertEquals(1, notifications.size)
        assertEquals("Nouvelle alerte", notifications[0].title)
    }

    @Test(expected = NotificationException::class)
    fun `notification should not be added if title is empty`() = runBlocking {
        val notification = Notification(
            id = 2,
            title = "",
            selectedHour = "10:00",
            description = "Description valide",
            days = "Une fois",
            timeLeft = "45",
            active = false
        )
        upsertNotificationUseCase(notification)
    }

    @Test(expected = NotificationException::class)
    fun `notification should not be added if description is empty`() = runBlocking {
        val notification = Notification(
            id = 3,
            title = "Titre valide",
            selectedHour = "09:00",
            description = "",
            days = "Mercredi",
            timeLeft = "60",
            active = true
        )
        upsertNotificationUseCase(notification)
    }
}
