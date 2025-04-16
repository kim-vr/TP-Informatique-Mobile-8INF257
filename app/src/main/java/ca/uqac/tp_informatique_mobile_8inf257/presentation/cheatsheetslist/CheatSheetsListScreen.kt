package ca.uqac.tp_informatique_mobile_8inf257.presentation.cheatsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ca.uqac.tp_informatique_mobile_8inf257.navigation.Screen
import ca.uqac.tp_informatique_mobile_8inf257.presentation.components.CheatSheetItem

@Composable
fun CheatSheetsListScreen(navController: NavController) {
    val viewModel: CheatSheetsListViewModel = hiltViewModel()
    val cheatSheets by viewModel.cheatSheets

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddCheatSheetScreen.route)
                },
                modifier = Modifier.background(Color.White)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Ajouter une fiche")
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
                text = "Mes fiches",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(fontSize = 36.sp)
            )
            HorizontalDivider()

            LazyColumn {
                items(cheatSheets) { cheatSheet ->
                    CheatSheetItem(cheatSheet)
                    HorizontalDivider()
                }
            }

        }
    }
}
