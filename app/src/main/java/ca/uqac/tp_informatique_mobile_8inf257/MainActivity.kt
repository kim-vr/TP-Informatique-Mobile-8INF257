package ca.uqac.tp_informatique_mobile_8inf257

import Menu
import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import ca.uqac.tp_informatique_mobile_8inf257.data.source.CheatSheetsDatabase
import ca.uqac.tp_informatique_mobile_8inf257.data.source.NotificationsDatabase
import ca.uqac.tp_informatique_mobile_8inf257.data.source.ToDoListDatabase
import ca.uqac.tp_informatique_mobile_8inf257.navigation.Screen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.addcheatsheet.AddCheatSheetScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.addquiz.AddQuizScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.addtodo.AddToDoScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.addtodo.AddToDoViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.cheatsheet.CheatSheetScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.cheatsheet.CheatSheetViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.cheatsheet.CheatSheetsListScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.home.HomeScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationsScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationScreenViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.quiz.QuizScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.quizzeslist.QuizzesListScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist.ToDoListScreen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist.ToDoListViewModel
import ca.uqac.tp_informatique_mobile_8inf257.ui.theme.TPInformatiqueMobile8INF257Theme
import dagger.hilt.android.AndroidEntryPoint

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

    private fun requestLocalisationPermission(){
        when {
            ContextCompat.checkSelfPermission(
                this, ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {}

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, ACCESS_FINE_LOCATION
            ) -> {
                requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
            }
            else -> {
                requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestLocalisationBackgroundPermission(){
        when {
            ContextCompat.checkSelfPermission(
                this, ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {}

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, ACCESS_BACKGROUND_LOCATION
            ) -> {
                requestPermissionLauncher.launch(ACCESS_BACKGROUND_LOCATION)
            }
            else -> {
                requestPermissionLauncher.launch(ACCESS_BACKGROUND_LOCATION)
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        requestLocalisationPermission()
        requestLocalisationBackgroundPermission()
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
                            NotificationsScreen(navController)
                        }

                        composable(route = Screen.TodoListScreen.route) {
                            ToDoListScreen(navController)
                        }

                        composable(route = Screen.AddToDoScreen.route) {
                            AddToDoScreen(navController)
                        }

                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController)
                        }

                        composable(route = Screen.CheatSheetsListScreen.route) {
                            CheatSheetsListScreen(navController)
                        }

                        composable(route = Screen.AddCheatSheetScreen.route) {
                            AddCheatSheetScreen(navController)
                        }

                        composable(
                            route = Screen.CheatSheetScreen.route,
                            arguments = listOf(
                                navArgument("cheatSheetId") {
                                    type = NavType.IntType
                                    nullable = false
                                    defaultValue = -1
                                }
                            )
                        ) { backStackEntry ->
                            val cheatSheetId = backStackEntry.arguments?.getInt("cheatSheetId", -1) ?: -1
                            CheatSheetScreen(
                                navController = navController,
                                cheatSheetId = cheatSheetId
                            )
                        }
                        composable(route = Screen.QuizzesListScreen.route) {
                            QuizzesListScreen(navController)
                        }
                        composable(route = Screen.AddQuizScreen.route) {
                            AddQuizScreen(navController)
                        }
                        composable(
                            route = Screen.QuizScreen.route,
                            arguments = listOf(
                                navArgument("quizId") {
                                    type = NavType.IntType
                                    nullable = false
                                    defaultValue = -1
                                }
                            )
                        ) { backStackEntry ->
                            val quizId = backStackEntry.arguments?.getInt("quizId", -1) ?: -1
                            QuizScreen(
                                navController = navController,
                                quizId = quizId
                            )
                        }
                    }
                }
            }
        }
    }
}

