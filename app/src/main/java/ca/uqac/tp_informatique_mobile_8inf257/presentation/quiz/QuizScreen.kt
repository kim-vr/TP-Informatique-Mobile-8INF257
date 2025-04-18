package ca.uqac.tp_informatique_mobile_8inf257.presentation.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ca.uqac.tp_informatique_mobile_8inf257.presentation.QuizVM


@Composable
fun QuizScreen(
    navController: NavController,
    quizId: Int
) {
    val viewModel: QuizViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Charger le quiz lorsque l'écran est affiché
    LaunchedEffect(quizId) {
        viewModel.loadQuiz(quizId)
    }

    // Affichage du quiz si disponible
    uiState.quiz?.let { quiz: QuizVM ->

        Scaffold(
            topBar = {
                Text(
                    text = quiz.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = TextStyle(fontSize = 28.sp)
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Afficher chaque question
                quiz.questions.forEachIndexed { qIndex, question ->
                    Text(
                        text = "Q${qIndex + 1}: ${question.text}",
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Afficher les réponses
                    question.answers.forEachIndexed { aIndex, answer ->

                        // Déterminer si la réponse est correcte
                        val isCorrect = aIndex == question.correctAnswerIndex
                        val isSelected = uiState.userAnswers[qIndex] == aIndex

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    viewModel.selectAnswer(qIndex, aIndex)
                                },
                                enabled = !uiState.showCorrection
                            )
                            Text(
                                text = answer.text,
                                modifier = Modifier.padding(start = 8.dp),
                                color = when {
                                    uiState.showCorrection && isSelected -> if (isCorrect) Color.Green else Color.Red
                                    else -> Color.Black
                                }
                            )
                        }

                        // Afficher la bonne réponse sous la question
                        if (uiState.showCorrection && isCorrect && isSelected) {
                            Text(
                                text = "Bonne réponse : ${answer.text}",
                                color = Color.Green,
                                style = TextStyle(fontSize = 16.sp),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Bouton pour afficher la correction
                Button(
                    onClick = {
                        viewModel.showCorrection()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Corriger")
                }

                // Affichage des résultats
                if (uiState.showCorrection) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Résultats : ${uiState.correctAnswersCount}/${quiz.questions.size} bonnes réponses",
                        style = TextStyle(fontSize = 18.sp, color = Color.Green)
                    )
                    Button(
                        onClick = { viewModel.resetQuiz() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Recommencer")
                    }
                }
            }
        }
    } ?: run {
        // Affichage pendant le chargement du quiz
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
