package ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications


import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications.NotificationsUseCases
import ca.uqac.tp_informatique_mobile_8inf257.presentation.NotificationVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.toEntity
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationScreenViewModel @Inject constructor
    (private val notificationsUseCases: NotificationsUseCases) : ViewModel() {
    private val _notificationsList = mutableStateOf<List<NotificationVM>>(emptyList())
    val notificationsList: State<List<NotificationVM>> = _notificationsList

    var job: Job? = null

    init {
        loadNotifications()
    }

    private fun loadNotifications(){
        job?.cancel()

        job = notificationsUseCases.getNotifications().onEach { notifications ->
            _notificationsList.value = notifications.map {
                NotificationVM.fromEntity(it)
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: NotificationsEvent) {
        when (event) {
            is NotificationsEvent.ChangeIsActive -> {
                activateNotification(event.notification)
            }

            is NotificationsEvent.Modify -> TODO()
            is NotificationsEvent.AddNotification -> {
                addNotification(event.notification)
            }
        }
    }

    private fun addNotification(notification: NotificationVM) {
        viewModelScope.launch {
            notificationsUseCases.upsertNotification(notification.toEntity())
        }
    }

    private fun activateNotification(notification: NotificationVM) {
        notification.active.value = !notification.active.value
        viewModelScope.launch {
            notificationsUseCases.upsertNotification(notification.toEntity())
        }
    }

    fun createGeoFence(context: Context, latitude: Double, longitude: Double){
        val geofencingClient = LocationServices.getGeofencingClient(context)

        val geofence = Geofence.Builder()
            .setRequestId("LieuImportant")
            .setCircularRegion(
                latitude,
                longitude,
                100f
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        val intent = Intent(context, BroadCastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
            .addOnSuccessListener {
                Log.d("Geofence", "Geofence ajouté")
            }
            .addOnFailureListener {
                Log.e("Geofence", "Erreur : ${it.message}")
            }
    }




}