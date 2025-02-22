package fr.equipe8.projetinfomobile.ui.routinescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.equipe8.projetinfomobile.R
import fr.equipe8.projetinfomobile.viewmodels.RoutineScreenViewModel

@Composable
fun RoutineScreen(navController: NavController, viewModel: RoutineScreenViewModel) {
    val routines = viewModel.routines.collectAsState()

    val sfFontFamily = FontFamily(
        Font(R.font.sf, FontWeight.Normal)) // Emplacement de la police : res/font
    Scaffold(
        floatingActionButton = {
            FloatingActionButton (onClick = {
                navController.navigate("addEditRoutineScreen?routineId=-1") //-1 = New routine
            }) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Ajouter une Routine")
            }
    }) {contentPadding->
        Column(modifier = Modifier.padding(contentPadding)) {
            Text(" Mes Routines",
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = sfFontFamily, // application de la police au texte
                    fontWeight = FontWeight.Bold
                )
            )
            Text("Total de routines: ${routines.value.count()}",
                modifier = Modifier
                    .padding(8.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = sfFontFamily
                )
            )
            Spacer(Modifier.height(8.dp)
            )
            LazyColumn {
                itemsIndexed(routines.value) { _, routine ->
                    RoutineCard(routine,
                        {
                            navController.navigate("addEditRoutineScreen?routineId=${routine.id}")
                        }
                    )
                }
            }
        }
    }
}