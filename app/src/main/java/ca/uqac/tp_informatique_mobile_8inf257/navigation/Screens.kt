package ca.uqac.tp_informatique_mobile_8inf257.navigation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home_screen")
    data object NotificationsScreen : Screen(route = "notifications_screen")
    data object TodoListScreen : Screen(route = "todo_list_screen")
    data object AddToDoScreen : Screen(route = "add_todo_screen")
    data object CheatSheetsListScreen : Screen(route = "cheat_sheets_list")
    data object CheatSheetScreen : Screen(route = "cheat_sheet/cheatSheetId={cheatSheetId}") {
        fun withCheatSheetId(cheatSheetId: Int): String = "cheat_sheet/cheatSheetId=$cheatSheetId"
    }
    data object AddCheatSheetScreen : Screen(route = "add_cheat_sheet_screen")
}