package fr.equipe8.projetinfomobile.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import fr.equipe8.projetinfomobile.data.Routine
import fr.equipe8.projetinfomobile.presentation.components.RoutineCard

@Composable
fun ListScreen(navController: NavController,viewModel: ListScreenViewModel) {
    Scaffold() {contentPading->
        Column(modifier = Modifier.padding(contentPading)) {
            Text(text = "Routines",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(fontSize = 32.sp,
                    textAlign = TextAlign.Center))
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(viewModel.routines.value){routine :Routine->
                    RoutineCard(routine = routine)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

    }
}