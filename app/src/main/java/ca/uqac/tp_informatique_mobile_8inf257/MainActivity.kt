package ca.uqac.tp_informatique_mobile_8inf257

import Menu
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.uqac.tp_informatique_mobile_8inf257.navigation.Screen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.addtodo.AddToDoScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.addtodo.AddToDoViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.home.HomeScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationsScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationScreenViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.hilt.NotificationViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist.ToDoListScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist.ToDoListViewModel
import ca.uqac.tp_informatique_mobile_8inf257.ui.theme.TPInformatiqueMobile8INF257Theme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(null, "Permission accordée")
        } else {
            Log.d(null, "Permission refusée")
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this, POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> { // Permission déjà accordée
                }

                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, POST_NOTIFICATIONS
                ) -> {
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
                else -> {
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
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
                            val reminders =  viewModel<NotificationScreenViewModel>()
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
                            val reminders =  viewModel<NotificationScreenViewModel>()
                            HomeScreen(navController, todolist, reminders)
                        }
                    }
                }
            }
        }
    }
}



