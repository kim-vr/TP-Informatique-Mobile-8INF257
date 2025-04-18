package ca.uqac.tp_informatique_mobile_8inf257.utils

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.BroadCastReceiver
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

fun addGeofence(context: Context, latitude: Double, longitude: Double, title: String) {
    val geofencingClient = LocationServices.getGeofencingClient(context)

    val geofence = Geofence.Builder()
        .setRequestId(title)
        .setCircularRegion(latitude, longitude, 100f)
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

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        Log.e("Geofence", "Permission non accordée")
        return
    }

    geofencingClient.addGeofences(geofencingRequest, pendingIntent)
        .addOnSuccessListener {
            Log.d("Geofence", "Geofence ajouté avec succès")
        }
        .addOnFailureListener {
            Log.e("Geofence", "Erreur lors de l'ajout : ${it.message}")
        }
}
