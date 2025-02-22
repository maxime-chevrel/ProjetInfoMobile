package fr.equipe8.projetinfomobile.ui.addeditscreen

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fr.equipe8.projetinfomobile.R
import fr.equipe8.projetinfomobile.viewmodels.AddEditViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(navController: NavController, routineId: Long?) {
    val viewModel: AddEditViewModel = hiltViewModel()
    val routine by viewModel.routine.collectAsState()

    val context = LocalContext.current

    val sfFontFamily = FontFamily(
        Font(R.font.sf, FontWeight.Normal)
    ) // variable contenant la police

    val calendar = Calendar.getInstance()
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            viewModel.onRoutineChanged(routine.copy(hour = selectedHour, minute = selectedMinute))
        },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )

    LaunchedEffect(routineId) {
        viewModel.getRoutineById(routineId)
    }

    Scaffold(
        topBar = {
            // Pour afficher si on modifie/ajoute
            TopAppBar(
                title = {
                    Text(
                        text = if (routineId == -1L) "Ajouter une routine" else "Modifier une routine",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = sfFontFamily
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween //
            ) {
                // Bouton de retour
                FloatingActionButton(
                    onClick = {
                        navController.navigate("routineScreen") {

                        }
                    },
                ) {
                    Icon(Icons.Filled.ArrowBack, "Retour")
                }


                FloatingActionButton(onClick = {
                    if (routineId == -1L) {
                        if (viewModel.routine.value.name != "") {
                            viewModel.addRoutine()
                        } else {
                            return@FloatingActionButton
                        }
                    } else if (viewModel.isRoutineEdited.value) {
                        viewModel.saveRoutine()
                    }
                    navController.navigate("routineScreen")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Sauvegarder"
                    )
                }
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = routine.name,
                label = { Text("Nom") },
                onValueChange = {
                    viewModel.onRoutineChanged(routine.copy(name = it))
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                value = routine.description,
                label = { Text("Description") },
                onValueChange = {
                    viewModel.onRoutineChanged(routine.copy(description = it))
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                )
            )
            Button(onClick = {
                timePickerDialog.show()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Heure: ${routine.hour}:${routine.minute}")
            }
        }
    }
}