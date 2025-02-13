package ca.uqac.tp_informatique_mobile_8inf257.navigation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home_screen")
    data object RemindersScreen : Screen(route = "reminders_screen")
}