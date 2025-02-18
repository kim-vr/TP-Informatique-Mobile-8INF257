package ca.uqac.tp_informatique_mobile_8inf257.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class ReminderVM(
    val id: Int = Random.nextInt(),
    val title: String = "",
    val selectedHour: String = "00:00",
    val description: String = "",
    val days: String = "Une fois",
    val timeLeft: String = "",
    var active: MutableState<Boolean> = mutableStateOf(false),
)
