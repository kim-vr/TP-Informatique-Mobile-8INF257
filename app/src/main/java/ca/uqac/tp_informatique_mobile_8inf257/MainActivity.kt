package ca.uqac.tp_informatique_mobile_8inf257

import Menu
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.uqac.tp_informatique_mobile_8inf257.navigation.Screen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.addtodo.AddToDoScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.addtodo.AddToDoViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.home.HomeScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationsScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationsViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist.ToDoListScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist.ToDoListViewModel
import ca.uqac.tp_informatique_mobile_8inf257.ui.theme.TPInformatiqueMobile8INF257Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TPInformatiqueMobile8INF257Theme {
                val navController = rememberNavController()
                var selectedItem by remember { mutableStateOf(0) }
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    Menu(
                        navController = navController,
                        selectedItem = selectedItem,
                        onItemSelected = { index -> selectedItem = index }
                    )
                }) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.NotificationsScreen.route) {
                            val reminders =  viewModel<NotificationsViewModel>()
                            NotificationsScreen(navController, reminders)
                        }

                        composable(route = Screen.TodoListScreen.route) {
                            val todolist =  viewModel<ToDoListViewModel>()
                            ToDoListScreen(navController, todolist)
                        }

                        composable(route = Screen.AddToDoScreen.route) {
                            val addTodo =  viewModel<AddToDoViewModel>()
                            AddToDoScreen(navController, addTodo)
                        }

                        composable(route = Screen.HomeScreen.route) {
                            val todolist =  viewModel<ToDoListViewModel>()
                            val reminders =  viewModel<NotificationsViewModel>()
                            HomeScreen(navController, todolist, reminders)
                        }
                    }
                }
            }
        }
    }
}

