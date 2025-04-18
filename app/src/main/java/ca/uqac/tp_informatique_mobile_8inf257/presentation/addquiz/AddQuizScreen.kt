package ca.uqac.tp_informatique_mobile_8inf257.presentation.addquiz

import androidx.compose.ui.platform.LocalContext
import ca.uqac.tp_informatique_mobile_8inf257.presentation.components.QuestionItem
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ca.uqac.tp_informatique_mobile_8inf257.presentation.components.OutlinedTextFieldWithLabel

@Composable
fun AddQuizScreen(
    navController: NavController
) {
    val viewModel: AddQuizViewModel = hiltViewModel()
    val quizTitle by viewModel.quizTitle.collectAsState()
    val questions by viewModel.questions.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Titre du quiz
        TextField(
            value = quizTitle,
            onValueChange = { viewModel.updateQuizTitle(it) },
            label = { Text("Titre du quiz") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Liste des questions
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(questions) { questionIndex, question ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("Question ${questionIndex + 1}")

                    TextField(
                        value = question.text,
                        onValueChange = { viewModel.updateQuestionText(questionIndex, it) },
                        label = { Text("Énoncé de la question") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Liste des réponses
                    question.answers.forEachIndexed { answerIndex, answer ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = question.correctAnswerIndex == answerIndex,
                                onClick = {
                                    // Mettre à jour la réponse correcte dans le ViewModel
                                    viewModel.setCorrectAnswer(questionIndex, answerIndex)
                                }
                            )
                            TextField(
                                value = answer.text,
                                onValueChange = {
                                    viewModel.updateAnswerText(questionIndex, answerIndex, it)
                                },
                                label = { Text("Réponse ${answerIndex + 1}") },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Bouton pour ajouter une réponse
                    Button(
                        onClick = { viewModel.addAnswer(questionIndex) }
                    ) {
                        Text("Ajouter une réponse")
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }

        // Boutons pour ajouter une question ou sauvegarder
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { viewModel.addQuestion() }) {
                Text("Ajouter une question")
            }

            Button(onClick = {
                viewModel.saveQuiz(
                    onSuccess = { navController.popBackStack() },
                    onError = { message -> /* Affiche une snackbar si tu veux */ }
                )
            }) {
                Text("Enregistrer le quiz")
            }
        }

        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = error, color = Color.Red)
        }
    }
}
