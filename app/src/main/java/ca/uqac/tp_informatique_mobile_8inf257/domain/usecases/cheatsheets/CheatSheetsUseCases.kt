package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets


data class CheatSheetsUseCases (
    val getCheatSheets : GetCheatSheetsUseCase,
    val getCheatSheet: GetCheatSheetUseCase,
    val upsertCheatSheet: UpsertCheatSheetUseCase,
    val deleteCheatSheet: DeleteCheatSheetUseCase
)
