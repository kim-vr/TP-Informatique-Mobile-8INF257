package ca.uqac.tp_informatique_mobile_8inf257.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.uqac.tp_informatique_mobile_8inf257.presentation.QuestionWithAnswersVM

@Composable
fun QuestionItem(
    questionVM: QuestionWithAnswersVM,
    onTextChange: (String) -> Unit,
    onAddAnswer: () -> Unit,
    onAnswerTextChange: (Int, String) -> Unit,
    onMarkCorrect: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextFieldWithLabel(
            label = "Question",
            value = questionVM.text,
            onValueChange = onTextChange
        )

        Spacer(modifier = Modifier.height(8.dp))

        questionVM.answers.forEachIndexed { index, answer ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                OutlinedTextField(
                    value = answer.text,
                    onValueChange = { onAnswerTextChange(index, it) },
                    modifier = Modifier.weight(1f),
                    label = { Text("Réponse ${index + 1}") }
                )
                IconButton(onClick = { onMarkCorrect(index) }) {
                    Icon(
                        imageVector = if (answer.isCorrect) Icons.Default.Check else Icons.Default.Close,
                        contentDescription = "Réponse correcte",
                        tint = if (answer.isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
            }
        }

        TextButton(onClick = onAddAnswer) {
            Text("+ Ajouter une réponse")
        }
    }
}
