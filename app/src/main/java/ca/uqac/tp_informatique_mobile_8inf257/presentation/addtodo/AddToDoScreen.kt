package ca.uqac.tp_informatique_mobile_8inf257.presentation.addtodo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ca.uqac.tp_informatique_mobile_8inf257.navigation.Screen

@Composable
fun AddToDoScreen(navController: NavController) {

    val viewModel: AddToDoViewModel = hiltViewModel()

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddToDoEvent.AddToDo)
                    navController.navigate(Screen.TodoListScreen.route)
                }){
                Icon(imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Add Story")
            }
        }
    ) { contentPadding ->
        val todo = viewModel.todo.value
        Column (
            Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Text(
                text = "Ajouter une todo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = TextStyle(
                    fontSize = 36.sp,
                    textAlign = TextAlign.Start
                )
            )
            OutlinedTextField(
                value = todo.description,
                label = {Text("Description")},
                onValueChange = {
                    viewModel.onEvent(AddToDoEvent.EnteredDescription(it))
                },
                singleLine = false,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}