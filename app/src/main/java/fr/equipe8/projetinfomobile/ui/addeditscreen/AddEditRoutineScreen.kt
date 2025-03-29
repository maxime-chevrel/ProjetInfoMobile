package fr.equipe8.projetinfomobile.ui.addeditscreen

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import fr.equipe8.projetinfomobile.navigation.ScreenRoute
import fr.equipe8.projetinfomobile.viewmodels.AddEditRoutineViewModel
import kotlinx.coroutines.flow.collectLatest
import java.time.DayOfWeek
import java.util.Calendar

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditRoutineScreen(navController: NavController,
                         viewModel: AddEditRoutineViewModel= hiltViewModel()) {
    val routine by viewModel.routine.collectAsState()
    val sfFontFamily = FontFamily(
        Font(R.font.sf, FontWeight.Normal)
    )

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            viewModel.onEvent(AddEditRoutineEvent.ModifiedTime(selectedHour.toByte(),selectedMinute.toByte()))
        },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )


    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            // Pour afficher si on modifie/ajoute
            TopAppBar(
                title = {
                    Text(
                        text = if (viewModel.routineId == -1L) "Ajouter une routine" else "Modifier une routine",
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
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween //
            ) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(ScreenRoute.RoutinesListScreen.route)
                    },
                    modifier = Modifier.padding(start = 32.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Retour")
                }
                if(viewModel.routineId != -1L){
                    Button(onClick = {
                        viewModel.onEvent(AddEditRoutineEvent.DeleteRoutine)
                    }) {
                        Text("Supprimer")
                    }
                }
                FloatingActionButton(onClick = {
                        viewModel.onEvent(AddEditRoutineEvent.SaveRoutine)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Sauvegarder"
                    )
                }
            }
        },
        snackbarHost = {SnackbarHost(hostState = snackbarHostState)}
    ) { contentPadding ->

        LaunchedEffect(true) {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is AddEditRoutineUiEvent.SavedStory -> {
                        navController.navigate(ScreenRoute.RoutinesListScreen.route
                                +"?returnAction="+ event.returnAction.toString())
                    }
                    is AddEditRoutineUiEvent.ShowMessage -> {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = routine.name,
                    label = { Text("Nom") },
                    onValueChange = {
                        viewModel.onEvent(AddEditRoutineEvent.EnteredName(it))
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
                        viewModel.onEvent(AddEditRoutineEvent.EnteredDescription(it))
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
                    Text("Heure: %02d:%02d".format(routine.hour, routine.minute))
                }
                Row {
                    Text(text = "Répétition")
                    Checkbox(
                        checked = routine.repeat,
                        onCheckedChange = {
                            viewModel.onEvent(AddEditRoutineEvent.ModifiedRepetition)
                        }
                    )
                }
                if(viewModel.routine.value.repeat) {
                    Row {
                        DayOfWeek.entries.forEach { day ->
                            val isSelected = routine.daysOfWeek.contains(day)

                            Button(
                                onClick = {
                                    viewModel.onEvent(AddEditRoutineEvent.ModifiedDay(day))
                                }, colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) Color.Blue else Color.Gray
                                )
                            ) {
                                Text(day.name.take(1))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    }
}