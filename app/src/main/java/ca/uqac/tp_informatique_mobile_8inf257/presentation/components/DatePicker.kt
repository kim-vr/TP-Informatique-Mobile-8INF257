package ca.uqac.tp_informatique_mobile_8inf257.presentation.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun TimePicker(): String {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedTime by remember { mutableStateOf("") }

    // Crée un TimePickerDialog
    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, hour: Int, minute: Int ->
            var min= "$minute"
            var goodHour = "$hour"
            if (minute < 10) {
                min = "0$minute" // Format de l'heure
            }
            if (hour < 10) {
                goodHour = "0$hour" // Format de l'heure
            }

            selectedTime = "$goodHour:$min" // Format de l'heure

        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true // true pour le format 24h, false pour le format AM/PM
    )

    Column {
        Button(onClick = { timePickerDialog.show() }) {
            Text(text = "Sélectionner une heure")
        }
        Text(text = "Heure sélectionnée : $selectedTime")
        return selectedTime


    }
    return "Error"
}
