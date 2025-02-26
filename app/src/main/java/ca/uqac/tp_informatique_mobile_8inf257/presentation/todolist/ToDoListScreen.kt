package ca.uqac.tp_informatique_mobile_8inf257.presentation.todolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.uqac.tp_informatique_mobile_8inf257.navigation.Screen

@Composable
fun ToDoListScreen(navController: NavController,
                   toDoListViewModel: ToDoListViewModel) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddToDoScreen.route)
            },
                Modifier.background(Color.Blue)) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add a to do")
            }
        }
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Todo à réaliser",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(
                    fontSize = 36.sp,
                    textAlign = TextAlign.Start
                )
            )
            HorizontalDivider()
            LazyColumn {
                items(toDoListViewModel.uncheckedTodos.value) { todo ->
                    Row (
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            todo.description,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 32.sp
                            )
                        )
                        Checkbox(
                            checked = todo.done,
                            onCheckedChange = {
                                toDoListViewModel.onEvent(ToDoEvent.Check(todo))
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }
            HorizontalDivider()
            Text(
                text = "Todo réalisées",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(
                    fontSize = 36.sp,
                    textAlign = TextAlign.Start
                )
            )
            LazyColumn {
                items(toDoListViewModel.checkedTodos.value) { todo ->
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            todo.description,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 32.sp
                            )
                        )
                        Checkbox(
                            checked = todo.done,
                            onCheckedChange = {
                                toDoListViewModel.onEvent(ToDoEvent.Check(todo))
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }

    }
}