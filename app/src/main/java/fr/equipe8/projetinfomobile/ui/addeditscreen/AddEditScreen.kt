package fr.equipe8.projetinfomobile.ui.addeditscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fr.equipe8.projetinfomobile.viewmodels.AddEditViewModel

@Composable
fun AddEditScreen(navController: NavController, routineId: Long?) {
    val viewModel: AddEditViewModel = hiltViewModel()
    val routine by viewModel.routine.collectAsState()

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
            } else {
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
            TextField(value = routine.name,
                label = { Text("Name")},
                onValueChange = {
                viewModel.onRoutineChanged(routine.copy(name=it))
            })
            TextField(value = routine.description,
                label = { Text("Description")},
                onValueChange = {
                viewModel.onRoutineChanged(routine.copy(description = it))
            })
        }
    }
}