package fr.equipe8.projetinfomobile.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

@Composable
fun ListScreen(viewModel: ViewModel) {
    Scaffold() {contentPading->
        Column(modifier = Modifier.padding(contentPading)) {
            Text(text = "Routine")
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {

            }
        }

    }
}