package fr.equipe8.projetinfomobile.ui.routinescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fr.equipe8.projetinfomobile.data.routines.Routine

@Composable
fun RoutineCard(routine: Routine){
    Box(modifier = Modifier.fillMaxSize()){
        Column{
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text(text = routine.name)
                Text(text = "" + routine.hour + ":" + routine.minute, textAlign = TextAlign.End)
            }
            Text(text = routine.description)
        }
    }
}