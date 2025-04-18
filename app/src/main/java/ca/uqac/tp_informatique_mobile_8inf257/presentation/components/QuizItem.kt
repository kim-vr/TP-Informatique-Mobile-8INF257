package ca.uqac.tp_informatique_mobile_8inf257.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.uqac.tp_informatique_mobile_8inf257.presentation.QuizVM

@Composable
fun QuizItem(
    quiz: QuizVM,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(10.dp)
    ) {
        Text(text = quiz.title, fontSize = 20.sp)
    }
}
