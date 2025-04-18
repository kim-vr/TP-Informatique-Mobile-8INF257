import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.saveable.rememberSaveable
import ca.uqac.tp_informatique_mobile_8inf257.presentation.components.TimePicker
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationScreenViewModel
import ca.uqac.tp_informatique_mobile_8inf257.utils.addGeofence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

@Composable
fun CustomModal(
    showModal: Boolean,
    onDismiss: () -> Unit,
    onAddNotification: (String, String, String, String) -> Unit,
    notificationScreenViewModel: NotificationScreenViewModel
) {
    if (showModal) {
        val context = LocalContext.current
        val daysSelected = remember { mutableStateMapOf<String, Boolean>() }

        if (daysSelected.isEmpty()) {
            listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche").forEach { day ->
                daysSelected[day] = false
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                    var notificationTitle by rememberSaveable { mutableStateOf("") }
                    var notificationDescription by rememberSaveable { mutableStateOf("") }
                    var notificationHour by rememberSaveable { mutableStateOf("18:00") }
                    var address by rememberSaveable { mutableStateOf("") }
                    var city by rememberSaveable { mutableStateOf("") }
                    var postalCode by rememberSaveable { mutableStateOf("") }
                    var isErrorVisible by remember { mutableStateOf(false) }

                    TextField(
                        value = notificationTitle,
                        onValueChange = { notificationTitle = it },
                        label = { Text("Quel est le titre de l'évènement ?") }
                    )

                    TextField(
                        value = notificationDescription,
                        onValueChange = { notificationDescription = it },
                        label = { Text("Quelle est la description de l'évènement ?") },
                        modifier = Modifier.padding(16.dp)
                    )

                    Row(modifier = Modifier.padding(16.dp)) {
                        val orderedDays = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
                        orderedDays.forEach { day ->
                            val isSelected = daysSelected[day] ?: false
                            val buttonColor = if (isSelected) Color.Black else Color.Gray

                            Box(
                                modifier = Modifier.size(35.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = { daysSelected[day] = !isSelected },
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                                ) {
                                    Text(day.first().toString(), fontSize = 15.sp)
                                }
                            }
                        }
                    }

                    notificationHour = TimePicker()

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Adresse") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("Ville") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    TextField(
                        value = postalCode,
                        onValueChange = { postalCode = it },
                        label = { Text("Code postal") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (isErrorVisible) {
                        Text("Un champ n'est pas rempli", color = Color.Red)
                    }

                    Row {
                        Button(onClick = onDismiss) {
                            Text("Annuler")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(onClick = {
                            if (
                                notificationTitle.isEmpty() ||
                                daysSelected.values.all { !it } ||
                                notificationHour.isEmpty() ||
                                address.isEmpty() || city.isEmpty() || postalCode.isEmpty()
                            ) {
                                isErrorVisible = true
                                return@Button
                            }

                            val fullAddress = "$address, $postalCode $city, France"
                            val geocoder = Geocoder(context, Locale.getDefault())
                            val location = geocoder.getFromLocationName(fullAddress, 1)
                            Log.d(null, "l'adresse est la suivante" + location.toString())

                            if (location != null && location.isNotEmpty()) {
                                val latitude = location[0].latitude
                                val longitude = location[0].longitude

                                val orderedDays = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
                                val daysSelectedString = daysSelected.filterValues { it }
                                    .keys
                                    .sortedWith(compareBy { orderedDays.indexOf(it) })
                                    .joinToString(", ")

                                onAddNotification(notificationTitle, notificationDescription, notificationHour, daysSelectedString)

                                addGeofence(
                                    context = context,
                                    latitude = latitude,
                                    longitude = longitude,
                                    title = notificationTitle
                                )

                                onDismiss()
                            } else {
                                isErrorVisible = true
                            }

                        }) {
                            Text("Ajouter")
                        }
                    }
                }
            }
        }
    }
}
