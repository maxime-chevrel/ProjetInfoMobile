package fr.equipe8.projetinfomobile.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.equipe8.projetinfomobile.data.Routine
import fr.equipe8.projetinfomobile.ui.theme.GrayBack

@Composable
fun RoutineCard(routine: Routine){
    Box(modifier = Modifier.fillMaxSize()
        .background(
            color = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
        .padding(8.dp)){
        Column(modifier = Modifier.padding(8.dp)){
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text(text = routine.name, fontSize = 28.sp, maxLines = 1,
                    overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                Text(text = "%02d:%02d".format(routine.hour,routine.minute), textAlign = TextAlign.End,
                    fontSize = 28.sp, maxLines = 1)
            }
            Text(text = routine.description+"  "+routine.id, fontSize = 20.sp, maxLines = 3,
                overflow = TextOverflow.Ellipsis)
        }
    }
}