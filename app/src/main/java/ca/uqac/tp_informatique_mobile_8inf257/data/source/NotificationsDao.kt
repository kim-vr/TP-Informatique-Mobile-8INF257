package ca.uqac.tp_informatique_mobile_8inf257.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationsDao {

    @Query("SELECT * FROM notifications")
    fun getNotifications() : Flow<List<Notification>>

    @Query("SELECT * FROM notifications WHERE ID = :id")
    fun getNotification(id: Int) : Notification?

    @Upsert
    suspend fun upsertNotification(notification: Notification)

    @Delete
    suspend fun deleteNotification(notification: Notification)
}