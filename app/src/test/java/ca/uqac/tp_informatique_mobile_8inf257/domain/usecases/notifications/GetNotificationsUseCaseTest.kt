package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseNotifications
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetNotificationsUseCaseTest {
    private lateinit var getNotificationsUseCase: GetNotificationsUseCase
    private val fakeDao = FakeDatabaseNotifications()

    @Before
    fun setUp() {
        getNotificationsUseCase = GetNotificationsUseCase(fakeDao)

        runBlocking {
            fakeDao.upsertNotification(
                Notification(
                    id = 1,
                    title = "Notification 1",
                    selectedHour = "07:00",
                    description = "Première alerte",
                    days = "Lundi",
                    timeLeft = "30",
                    active = true
                )
            )
            fakeDao.upsertNotification(
                Notification(
                    id = 2,
                    title = "Notification 2",
                    selectedHour = "08:00",
                    description = "Deuxième alerte",
                    days = "Mardi",
                    timeLeft = "45",
                    active = false
                )
            )
        }
    }

    @Test
    fun `should return all notifications from dao`() = runBlocking {
        val notifications = getNotificationsUseCase().first()

        assertEquals(2, notifications.size)
        assertTrue(notifications.any { it.title == "Notification 1" })
        assertTrue(notifications.any { it.title == "Notification 2" })
    }

    @Test
    fun `should return empty list when no notifications exist`() = runBlocking {
        fakeDao.notifications.clear()

        val notifications = getNotificationsUseCase().first()
        assertTrue(notifications.isEmpty())
    }
}
