package ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.hilt

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import android.Manifest
import java.time.ZoneId

@HiltViewModel
class NotificationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat
) : ViewModel() {

    // Fonction pour planifier une notification
    public fun scheduleNotificationsForDays(now: LocalDateTime, selectedDays: List<DayOfWeek>, selectedTime: LocalTime, title: String, description: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        selectedDays.forEach { dayOfWeek ->
            // Calculer l'heure de la notification pour ce jour
            val targetDateInMillis = getNextOccurrenceForDay(now, dayOfWeek, selectedTime)

            // Créer un Intent pour la notification
            val intent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra("title", title)
                putExtra("description", description)
            }

            // Créer un PendingIntent
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                dayOfWeek.ordinal, // Utiliser l'ordinal du jour comme identifiant unique
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // Planifier la notification via AlarmManager
            try {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetDateInMillis, pendingIntent)
            } catch (e: SecurityException) {
                Toast.makeText(context, "Permission refusée. Veuillez activer les permissions.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getNextOccurrenceForDay(now: LocalDateTime, dayOfWeek: DayOfWeek, selectedTime: LocalTime): Long {
        // Fixer l'heure de la notification pour ce jour particulier
        var targetDate = now.withHour(selectedTime.hour).withMinute(selectedTime.minute).withSecond(0).withNano(0)

        // Vérifier si l'heure de la notification est déjà passée pour ce jour
        val currentDayOfWeek = now.dayOfWeek
        var daysToAdd = dayOfWeek.value - currentDayOfWeek.value

        // Si le jour est déjà passé ou si c'est le même jour mais que l'heure est déjà passée, planifier pour le jour suivant
        if (daysToAdd < 0 || (daysToAdd == 0 && now.isAfter(targetDate))) {
            // Ajouter 7 jours pour planifier la notification pour le même jour de la semaine, la semaine suivante
            daysToAdd += 7
        }

        // Ajouter les jours nécessaires pour atteindre la prochaine occurrence
        targetDate = targetDate.plusDays(daysToAdd.toLong())

        // Retourner l'heure cible en millisecondes
        return targetDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }


    // Convertir un jour de la semaine en DayOfWeek (ex : "Lundi" => MONDAY)
    fun convertStringToDayOfWeek(day: String): DayOfWeek {
        return when (day) {
            "Lundi" -> DayOfWeek.MONDAY
            "Mardi" -> DayOfWeek.TUESDAY
            "Mercredi" -> DayOfWeek.WEDNESDAY
            "Jeudi" -> DayOfWeek.THURSDAY
            "Vendredi" -> DayOfWeek.FRIDAY
            "Samedi" -> DayOfWeek.SATURDAY
            "Dimanche" -> DayOfWeek.SUNDAY
            else -> throw IllegalArgumentException("Jour invalide: $day")
        }
    }






}
