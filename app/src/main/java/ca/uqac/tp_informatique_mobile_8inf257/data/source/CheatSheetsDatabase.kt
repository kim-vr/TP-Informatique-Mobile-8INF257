package ca.uqac.tp_informatique_mobile_8inf257.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet

@Database(entities = [CheatSheet::class], version = 1)
abstract class CheatSheetsDatabase : RoomDatabase() {
    abstract val dao : CheatSheetsDao

    companion object {
        const val DATABASE_NAME = "cheat_sheets.db"
    }
}
