package ca.uqac.tp_informatique_mobile_8inf257.presentation.addcheatsheet

import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ca.uqac.tp_informatique_mobile_8inf257.presentation.addcheatsheet.AddCheatSheetViewModel

@Composable
fun AddCheatSheetScreen(navController: NavController) {
    val viewModel: AddCheatSheetViewModel = hiltViewModel()
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var documentName by remember { mutableStateOf("Aucun document sélectionné") }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedUri = uri
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

            context.contentResolver.query(it, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (cursor.moveToFirst() && nameIndex != -1) {
                    documentName = cursor.getString(nameIndex)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Ajouter une fiche de révision", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Titre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            filePickerLauncher.launch("*/*") // Pour PDF, images, etc.
        }) {
            Text("Choisir un document")
        }

        Text("Sélectionné : $documentName", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && description.isNotBlank() && selectedUri != null) {
                    viewModel.addCheatSheet(title, description, selectedUri.toString())
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ajouter la fiche")
        }
    }
}
