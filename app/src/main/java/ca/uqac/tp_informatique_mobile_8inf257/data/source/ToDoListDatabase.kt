package ca.uqac.tp_informatique_mobile_8inf257.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo


@Database(entities = [ToDo::class], version = 1)
abstract class ToDoListDatabase: RoomDatabase() {
    abstract val dao: ToDoListDao

    companion object {
        const val DATABASE_NAME = "todolist.db"
    }
}