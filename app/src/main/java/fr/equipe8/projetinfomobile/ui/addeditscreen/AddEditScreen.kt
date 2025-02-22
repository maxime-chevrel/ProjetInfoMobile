package fr.equipe8.projetinfomobile.ui.addeditscreen

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fr.equipe8.projetinfomobile.viewmodels.AddEditViewModel
import java.util.Calendar

@Composable
fun AddEditScreen(navController: NavController, routineId: Long?) {
    val viewModel: AddEditViewModel = hiltViewModel()
    val routine by viewModel.routine.collectAsState()


    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            viewModel.onRoutineChanged(routine.copy(hour=selectedHour, minute = selectedMinute))
        },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )

    LaunchedEffect(routineId) {
        viewModel.getRoutineById(routineId)
    }

    Scaffold(
        floatingActionButton = { FloatingActionButton(onClick = {
            if (routineId==-1L) {
                if (viewModel.routine.value.name!="") {
                    viewModel.addRoutine()
                } else {
                    return@FloatingActionButton
                }
            } else if (viewModel.isRoutineEdited.value) {
                viewModel.saveRoutine()
            }
            navController.navigate("routineScreen")
        }) {
            Icon(imageVector = Icons.Default.Add,
                contentDescription = "Sauvegarder")
            }
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            OutlinedTextField(value = routine.name,
                label = { Text("Name")},
                onValueChange = {
                viewModel.onRoutineChanged(routine.copy(name=it))
            })
            OutlinedTextField(value = routine.description,
                label = { Text("Description")},
                onValueChange = {
                viewModel.onRoutineChanged(routine.copy(description = it))
            })
            Button(onClick = {
                timePickerDialog.show()
            }) {
                Text("Time: ${routine.hour}:${routine.minute}")
            }
        }
    }
}