import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ca.uqac.tp_informatique_mobile_8inf257.navigation.Screen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.NavItem

@Composable
fun Menu(
    navController: NavController,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf("Accueil", "Routines", "Todo", "Fiches")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Notifications, Icons.Filled.Menu, Icons.Filled.Create)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.Notifications, Icons.Outlined.Menu, Icons.Outlined.Create)

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    onItemSelected(index) // Notifie la sélection
                    when (index) {
                        0 -> navController.navigate(Screen.HomeScreen.route)
                        1 -> navController.navigate(Screen.NotificationsScreen.route)
                        2 -> navController.navigate(Screen.TodoListScreen.route)
                        3 -> navController.navigate(Screen.CheatSheetsListScreen.route)
                    }
                }
            )
        }
    }
}
