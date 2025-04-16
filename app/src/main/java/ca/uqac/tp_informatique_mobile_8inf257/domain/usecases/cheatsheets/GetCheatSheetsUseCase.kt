package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets

import ca.uqac.tp_informatique_mobile_8inf257.data.source.CheatSheetsDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import kotlinx.coroutines.flow.Flow

class GetCheatSheetsUseCase(private val cheatSheetsDao: CheatSheetsDao) {
    operator fun invoke() : Flow<List<CheatSheet>> {
        return cheatSheetsDao.getCheatSheets()
    }
}