package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets

import ca.uqac.tp_informatique_mobile_8inf257.data.source.CheatSheetsDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet


class GetCheatSheetUseCase(private val cheatSheetsDao: CheatSheetsDao) {
    operator fun invoke(cheatSheetId: Int) : CheatSheet? {
        return cheatSheetsDao.getCheatSheet(cheatSheetId)
    }
}