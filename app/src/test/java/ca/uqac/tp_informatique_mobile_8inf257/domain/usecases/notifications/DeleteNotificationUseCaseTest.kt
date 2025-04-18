package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseNotifications
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteNotificationUseCaseTest {
    private lateinit var deleteNotificationUseCase: DeleteNotificationUseCase
    private val fakeDao = FakeDatabaseNotifications()

    @Before
    fun setUp() {
        deleteNotificationUseCase = DeleteNotificationUseCase(fakeDao)

        // Pré-remplir la base fake avec une notification
        runBlocking {
            fakeDao.upsertNotification(
                Notification(
                    id = 1,
                    title = "Notification à supprimer",
                    selectedHour = "09:00",
                    description = "Une alerte test",
                    days = "Vendredi",
                    timeLeft = "15",
                    active = true
                )
            )
        }
    }

    @Test
    fun `notification should be removed from database`() = runBlocking {
        val notification = fakeDao.getNotification(1)
        assertNotNull(notification)

        deleteNotificationUseCase(notification!!)

        val notifications = fakeDao.getNotifications().first()
        assertTrue(notifications.none { it.id == 1 })
    }

    @Test
    fun `deleting a non-existing notification should not crash`() = runBlocking {
        val fakeNotification = Notification(
            id = 999,
            title = "Inexistante",
            selectedHour = "00:00",
            description = "Jamais ajoutée",
            days = "Dimanche",
            timeLeft = "0",
            active = false
        )

        // Ne doit pas lancer d'exception
        deleteNotificationUseCase(fakeNotification)

        val notifications = fakeDao.getNotifications().first()
        assertEquals(1, notifications.size) // celle d'origine reste
    }
}