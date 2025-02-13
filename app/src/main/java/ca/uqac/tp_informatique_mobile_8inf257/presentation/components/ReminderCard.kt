package ca.uqac.tp_informatique_mobile_8inf257.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ReminderVM

@Composable
fun ReminderCard(reminder: ReminderVM) {
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
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(30.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray)
                        .padding(horizontal = 2.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .background(Color.White, CircleShape)
                    )
                }
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