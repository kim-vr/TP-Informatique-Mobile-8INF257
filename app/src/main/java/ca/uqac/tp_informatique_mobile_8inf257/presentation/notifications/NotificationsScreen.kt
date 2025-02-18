package ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications

import CustomModal
import androidx.compose.foundation.layout.Box
import ca.uqac.tp_informatique_mobile_8inf257.presentation.listreminders.ListRemindersViewModel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ReminderVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.components.NotificationsCard
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun NotificationsScreen(navController: NavController, notificationsViewModel: NotificationsViewModel) {
    var showModal by remember { mutableStateOf(false) }

    // Liste mutable des notifications ajoutées manuellement
    var customNotifications by remember { mutableStateOf(listOf<ReminderVM>()) }

    Scaffold { contentPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Column {
                Text(
                    text = "Mes Notifications",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = TextStyle(fontSize = 36.sp)
                )

                // Bouton pour afficher le modal
                Button(onClick = { showModal = true }) {
                    Text(text = "Ajouter une notification")
                }

                LazyColumn {
                    // Affiche les notifications existantes
                    items(notificationsViewModel.reminders.value) { reminder ->
                        HorizontalDivider(
                            color = Color.Gray.copy(alpha = 0.5f),
                            thickness = 1.dp
                        )
                        NotificationsCard(reminder, notificationsViewModel)
                    }

                    // Affiche les nouvelles notifications ajoutées manuellement
                    items(customNotifications) { reminder ->
                        HorizontalDivider(
                            color = Color.Gray.copy(alpha = 0.5f),
                            thickness = 1.dp
                        )
                        NotificationsCard(reminder, notificationsViewModel)
                    }
                }
            }

            // Modal pour ajouter une notification
            CustomModal(
                showModal = showModal,
                onDismiss = { showModal = false },
                onAddNotification = { title, description, hour, days ->
                    val timeLeft = calculateTimeLeft(days, hour) // Calculer le temps restant correctement
                    val newReminder = ReminderVM(
                        title = title,
                        description = description,
                        selectedHour = hour,
                        days = days,
                        timeLeft = timeLeft // Temps calculé
                    )
                    customNotifications = customNotifications + newReminder
                }
            )
        }
    }
}


fun calculateTimeLeft(days: String, hour: String): String {
    val orderedDays = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
    val selectedDays = days.split(", ").map { it.trim() }
    val selectedHour = LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm"))
    val now = LocalDateTime.now()

    var minTimeLeft: Long = Long.MAX_VALUE

    for (day in selectedDays) {
        val dayIndex = orderedDays.indexOf(day)
        val currentDayIndex = now.dayOfWeek.value - 1

        // Calculer la différence en jours
        var dayDifference = dayIndex - currentDayIndex

        if (dayDifference < 0 || (dayDifference == 0 && now.toLocalTime().isAfter(selectedHour))) {
            // Si le jour est passé ou si c'est aujourd'hui mais que l'heure est passée, déplacer à la semaine prochaine
            dayDifference += 7
        }

        // Créer la date et l'heure du prochain rappel
        val reminderDate = now.plusDays(dayDifference.toLong())
            .withHour(selectedHour.hour)
            .withMinute(selectedHour.minute)
            .withSecond(0)

        // Calculer le temps restant en minutes
        val timeLeftInMinutes = ChronoUnit.MINUTES.between(now, reminderDate)

        // Mettre à jour le temps minimum
        if (timeLeftInMinutes < minTimeLeft) {
            minTimeLeft = timeLeftInMinutes
        }
    }

    // Convertir les minutes en un format lisible
    return when {
        minTimeLeft < 60 -> "$minTimeLeft min"
        minTimeLeft < 24 * 60 -> "${minTimeLeft / 60}h"
        else -> "${minTimeLeft / (24 * 60)} jours"
    }
}

