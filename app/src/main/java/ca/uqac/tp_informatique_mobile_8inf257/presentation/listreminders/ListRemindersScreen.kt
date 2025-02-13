package ca.uqac.tp_informatique_mobile_8inf257.presentation.listreminders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.uqac.tp_informatique_mobile_8inf257.presentation.components.ReminderCard

@Composable
fun ListRemindersScreen(navController: NavController, remindersViewModel: ListRemindersViewModel) {
    Scaffold {
        contentPadding ->
        Column (
            Modifier
                .padding(contentPadding)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Mes Rappels",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(
                    fontSize = 36.sp
                )
            )

            Text(
                text = "Ajouter un rappel",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Blue
                )
            )

            LazyColumn {
                items(remindersViewModel.reminders.value) {reminder ->
                    HorizontalDivider(
                        color = Color.Gray.copy(alpha = 0.5f),
                        thickness = 1.dp
                    )
                    ReminderCard(reminder, remindersViewModel)
                }
            }
        }
    }
}