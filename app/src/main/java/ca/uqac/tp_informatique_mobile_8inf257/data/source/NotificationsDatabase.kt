package ca.uqac.tp_informatique_mobile_8inf257.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Notification

@Database(entities = [Notification::class], version = 1)
abstract class NotificationsDatabase : RoomDatabase() {
    abstract val dao: NotificationsDao

    companion object {
        const val DATABASE_NAME = "notifications.db"
    }
}