package ca.uqac.tp_informatique_mobile_8inf257.domain


import ca.uqac.tp_informatique_mobile_8inf257.data.source.CheatSheetsDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDatabaseCheatSheets : CheatSheetsDao {
    val cheatSheets = mutableListOf<CheatSheet>()

    override fun getCheatSheets(): Flow<List<CheatSheet>> = flow {
        emit(cheatSheets)
    }

    override fun getCheatSheet(id: Int): CheatSheet? {
        return cheatSheets.find { it.id == id }
    }

    override suspend fun upsertCheatSheet(cheatSheet: CheatSheet) {
        cheatSheets.removeIf { it.id == cheatSheet.id }
        cheatSheets.add(cheatSheet)
    }

    override suspend fun deleteCheatSheet(cheatSheet: CheatSheet) {
        cheatSheets.remove(cheatSheet)
    }
}
