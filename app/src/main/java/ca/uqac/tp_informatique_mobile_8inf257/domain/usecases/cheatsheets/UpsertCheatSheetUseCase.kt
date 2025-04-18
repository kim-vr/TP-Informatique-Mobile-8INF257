package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets

import ca.uqac.tp_informatique_mobile_8inf257.data.source.CheatSheetsDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import ca.uqac.tp_informatique_mobile_8inf257.utils.CheatSheetException
import kotlin.jvm.Throws

class UpsertCheatSheetUseCase(private val cheatSheetsDao: CheatSheetsDao) {
    @Throws(CheatSheetException::class)
    suspend operator fun invoke(cheatSheet: CheatSheet) {
        if (cheatSheet.title.isEmpty() || cheatSheet.document.isEmpty())
            throw CheatSheetException("Tous les champs obligatoires ne sont pas remplis")
        cheatSheetsDao.upsertCheatSheet(cheatSheet)
    }
}