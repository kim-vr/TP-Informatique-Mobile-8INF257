package ca.uqac.tp_informatique_mobile_8inf257.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.uqac.tp_informatique_mobile_8inf257.presentation.NotificationVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationsEvent
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationScreenViewModel

@Composable
fun NotificationsCard(reminder: NotificationVM, notificationScreenViewModel: NotificationScreenViewModel) {
    Box (
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ){
        Column {
            Text(
                reminder.title,
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    reminder.selectedHour,
                    style = TextStyle(
                        fontSize = 32.sp
                    )
                )
                Text(
                    reminder.description,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
                Switch(
                    checked = reminder.active.value, // Utilisation de MutableState ici
                    onCheckedChange = {
                        notificationScreenViewModel.onEvent(NotificationsEvent.ChangeIsActive(reminder))
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF4CAF50),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.Gray
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    reminder.days,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
                Text(
                    " | ",
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
                Text(
                    "Rappel dans " + reminder.timeLeft,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}