package ca.uqac.tp_informatique_mobile_8inf257

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.uqac.tp_informatique_mobile_8inf257.navigation.Screen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.listreminders.ListRemindersScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.listreminders.ListRemindersViewModel
import ca.uqac.tp_informatique_mobile_8inf257.ui.theme.TPInformatiqueMobile8INF257Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TPInformatiqueMobile8INF257Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.RemindersScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.RemindersScreen.route) {
                            val reminders =  viewModel<ListRemindersViewModel>()
                            ListRemindersScreen(navController, reminders)
                        }
                    }
                }
            }
        }
    }
}

