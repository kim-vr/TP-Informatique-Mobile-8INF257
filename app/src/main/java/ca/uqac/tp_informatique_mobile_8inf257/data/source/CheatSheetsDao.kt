package ca.uqac.tp_informatique_mobile_8inf257.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import kotlinx.coroutines.flow.Flow

@Dao
interface CheatSheetsDao {
    @Query("SELECT * FROM cheat_sheets")
    fun getCheatSheets() : Flow<List<CheatSheet>>

    @Query("SELECT * FROM cheat_sheets WHERE ID = :id")
    fun getCheatSheet(id: Int) : CheatSheet?

    @Upsert
    suspend fun upsertCheatSheet(cheatSheet: CheatSheet)

    @Delete
    suspend fun deleteCheatSheet(cheatSheet: CheatSheet)
}

