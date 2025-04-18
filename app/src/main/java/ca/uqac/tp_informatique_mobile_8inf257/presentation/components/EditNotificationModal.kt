package ca.uqac.tp_informatique_mobile_8inf257.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.sp
import ca.uqac.tp_informatique_mobile_8inf257.presentation.components.TimePicker
import ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications.NotificationScreenViewModel


@Composable
fun CustomModal(showModal: Boolean, onDismiss: () -> Unit, onAddNotification: (String, String, String, String) -> Unit, notificationScreenViewModel : NotificationScreenViewModel) {
    if (showModal) {
        val daysSelected = remember { mutableStateMapOf<String, Boolean>() }

        // Initialisation des jours avec "non sélectionnés"
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
                    var isActive by rememberSaveable { mutableStateOf(true) }

                    TextField(
                        value = notificationTitle,
                        onValueChange = { notificationTitle = it },
                        label = { Text("Quel est le titre de l'évènement ?") }
                    )

                    TextField(
                        value = notificationDescription,
                        onValueChange = { notificationDescription = it },
                        label = { Text("Quel est la description de l'évènement ?") },
                        modifier = Modifier.padding(16.dp)
                    )

                    // Sélection des jours avec boutons
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
                                    Text(day.first().toString(), fontSize = 15.sp) // Utilisation de la première lettre du jour
                                }
                            }
                        }
                    }

                    notificationHour = TimePicker()

                    Spacer(modifier = Modifier.height(16.dp))
                    var isErrorVisible by remember { mutableStateOf(false) }
                    if (isErrorVisible){
                        Text("Un champ n'est pas rempli", color = Color.Red)
                    }
                    Row {
                        Button(onClick = onDismiss) {
                            Text("Annuler")
                        }
                        Spacer(modifier = Modifier.width(16.dp))


                        Button(onClick = {
                            if (notificationTitle.isEmpty() || daysSelected.values.all { !it } || notificationHour.isEmpty()) {
                                isErrorVisible = true
                                return@Button
                            }
                            val orderedDays = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
                            val daysSelectedString = daysSelected.filterValues { it } // Garde seulement les jours sélectionnés
                                .keys // Récupère les noms des jours
                                .sortedWith(compareBy { orderedDays.indexOf(it) }) // Trie selon l'ordre défini dans orderedDays
                                .joinToString(separator = ", ")

                            onAddNotification(notificationTitle, notificationDescription, notificationHour, daysSelectedString)
                            onDismiss()
                        }) {
                            Text("Ajouter")
                        }
                    }
                }
            }
        }
    }
}

