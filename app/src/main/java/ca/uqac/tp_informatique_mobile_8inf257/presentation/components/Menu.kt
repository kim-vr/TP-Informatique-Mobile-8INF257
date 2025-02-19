import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import ca.uqac.tp_informatique_mobile_8inf257.presentation.NavItem

@Composable
fun Menu() {
    // Liste des éléments de navigation
    val navItems = listOf(
        NavItem("Accueil", Icons.Default.Home),
        NavItem("Routines", Icons.Default.Notifications),
        NavItem("Mes tâches", Icons.Default.Menu)
    )

    // Affichage de la barre de navigation
    NavigationBar (modifier = Modifier.height(80.dp)){
        navItems.forEach { item ->
            NavigationBarItem(
                selected = false, // Change en fonction de la navigation actuelle
                onClick = {
                    // Action lors du clic sur l'élément
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}
