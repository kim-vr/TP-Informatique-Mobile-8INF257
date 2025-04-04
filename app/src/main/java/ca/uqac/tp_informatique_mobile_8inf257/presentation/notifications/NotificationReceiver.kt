package ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.*

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Vérifier la permission de notification

            // Récupérer le titre et la description de l'intent
            val title = intent.getStringExtra("title") ?: "Notification"
            val description = intent.getStringExtra("description") ?: "Aucune description"

            // Créer la notification
            val notificationManager = NotificationManagerCompat.from(context)

            // Créer un ID de notification unique (par exemple, vous pouvez utiliser un numéro fixe ou une valeur dynamique)
            val notificationId = 1 // Vous pouvez aussi utiliser une valeur dynamique si vous avez besoin de plusieurs notifications

            val notification = NotificationCompat.Builder(context, "MainChannelID")
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Assurez-vous que l'icône est valide
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            // Envoyer la notification
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notificationManager.notify(notificationId, notification)
        }

    fun scheduleNotificationForSpecificDay(context: Context, title: String, description: String, dayOfWeek: Int, hour: Int, minute: Int) {
        // Créer un objet Calendar pour définir l'heure de la notification
        val calendar = Calendar.getInstance()

        // Fixer l'heure et le jour de la semaine
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek) // Exemple : Calendar.MONDAY, Calendar.TUESDAY, etc.
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // Si l'heure du jour est déjà passée, on planifie pour le jour suivant
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
        }

        // Créer l'intent pour envoyer la notification
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("description", description)
        }

        // Créer un PendingIntent
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Utiliser l'AlarmManager pour planifier la notification
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Planifier l'alarme
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    }
    }



