package ca.uqac.tp_informatique_mobile_8inf257.presentation.quizzeslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ca.uqac.tp_informatique_mobile_8inf257.navigation.Screen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.quizzeslist.QuizzesListViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.components.QuizItem

@Composable
fun QuizzesListScreen(navController: NavController) {
    val viewModel: QuizzesListViewModel = hiltViewModel()
    val quizzes by viewModel.quizzes

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddQuizScreen.route)
                },
                modifier = Modifier.background(Color.White)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Ajouter un quiz")
            }
        }
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Mes quiz",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(fontSize = 36.sp)
            )
            HorizontalDivider()

            LazyColumn {
                items(quizzes) { quiz ->
                    QuizItem(
                        quiz = quiz,
                        onClick = {
                            navController.navigate(
                                Screen.QuizScreen.withQuizId(quiz.id)
                            )
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
