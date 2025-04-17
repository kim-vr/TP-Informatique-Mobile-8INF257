package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets

import ca.uqac.tp_informatique_mobile_8inf257.data.source.CheatSheetsDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GetCheatSheetUseCase(private val cheatSheetsDao: CheatSheetsDao) {
    suspend operator fun invoke(cheatSheetId: Int): CheatSheet? = withContext(Dispatchers.IO) {
        cheatSheetsDao.getCheatSheet(cheatSheetId)
    }
}
