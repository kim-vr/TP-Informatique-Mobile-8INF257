package ca.uqac.tp_informatique_mobile_8inf257.navigation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home_screen")
    data object TodoListScreen : Screen(route = "todo_list_screen")
    data object AddToDoScreen : Screen(route = "add_todo_screen")
}