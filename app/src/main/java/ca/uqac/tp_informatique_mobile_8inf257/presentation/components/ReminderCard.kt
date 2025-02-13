package ca.uqac.tp_informatique_mobile_8inf257.presentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ReminderVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.listreminders.ListRemindersViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.listreminders.ReminderEvent
import ca.uqac.tp_informatique_mobile_8inf257.utils.buttonBackgroundColor

@Composable
fun ReminderCard(reminder: ReminderVM, remindersViewModel: ListRemindersViewModel) {
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
                    checked = reminder.active,
                    onCheckedChange = { remindersViewModel.onEvent(ReminderEvent.ChangeIsActive(reminder))},
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