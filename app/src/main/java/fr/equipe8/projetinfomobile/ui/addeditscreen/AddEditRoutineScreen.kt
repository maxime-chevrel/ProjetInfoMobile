package fr.equipe8.projetinfomobile.ui.addeditscreen

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
            TopAppBar(
                title = {
                    Text(
                        text = if (viewModel.routineId == -1L) "Ajouter une routine" else "Modifier une routine",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),modifier = Modifier.shadow(10.dp)
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
                    },shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)).clip(
                            RoundedCornerShape(12.dp)))
                    {
                        Text(text = "Supprimer",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

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
                    label = { Text(text = "Nom", style = MaterialTheme.typography.titleSmall) },
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
                    label = { Text(text = "Description", style = MaterialTheme.typography.titleSmall) },
                    onValueChange = {
                        viewModel.onEvent(AddEditRoutineEvent.EnteredDescription(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    timePickerDialog.show()
                },shape = MaterialTheme.shapes.medium,modifier = Modifier.fillMaxWidth().shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)).clip(
                    RoundedCornerShape(12.dp))) {
                    Text(text = "Heure: %02d:%02d".format(routine.hour, routine.minute),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row (verticalAlignment = Alignment.CenterVertically,
                    ) {
                    Text(text = "Routine active",
                        style = MaterialTheme.typography.titleMedium)
                    Checkbox(
                        checked = routine.isActive,
                        onCheckedChange = {
                            viewModel.onEvent(AddEditRoutineEvent.ModifiedRepetition)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                DropBoxMenu()
            }
        }
    }
}


@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
fun DropBoxMenu (viewModel: AddEditRoutineViewModel= hiltViewModel()){

    val routine by viewModel.routine.collectAsState()

    val periodOptions = listOf("Tous les jours", "Personnalisé...", "Aucun")
    var expanded by remember { mutableStateOf(false) }
    var selectPeriodicity by remember { mutableStateOf(periodOptions[0]) }


ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectPeriodicity,
            label = { Text(text = "Périodicité", style = MaterialTheme.typography.titleSmall) },
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.menuAnchor(),
            trailingIcon =  {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown, contentDescription = "Parcourir"
                )
            }
            )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize()
        ) {
            periodOptions.forEach { periodicity ->
                DropdownMenuItem(
                    text = { Text(text = periodicity, style = MaterialTheme.typography.titleMedium)  },
                    onClick = {
                        selectPeriodicity = periodicity
                        expanded = false

                    }
                )
            }
        }

    }

    if (selectPeriodicity == "Personnalisé...") {
        FlowRow(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DayOfWeek.entries.forEach { day ->
                val isSelected = routine.daysOfWeek.contains(day)
                FlowRow(Modifier.align(Alignment.CenterVertically)) {

                    Button(
                        onClick = {
                            viewModel.onEvent(AddEditRoutineEvent.ModifiedDay(day))
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = if (isSelected) 4.dp else 0.dp),

                        ) {
                        Text(
                            text = day.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }

            }
        }
    }
}