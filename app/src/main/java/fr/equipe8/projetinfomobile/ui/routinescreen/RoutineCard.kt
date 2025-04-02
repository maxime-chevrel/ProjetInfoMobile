package fr.equipe8.projetinfomobile.ui.routinescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.equipe8.projetinfomobile.R
import fr.equipe8.projetinfomobile.ui.RoutineVM
import fr.equipe8.projetinfomobile.ui.addeditscreen.PeriodOptions
import fr.equipe8.projetinfomobile.ui.theme.DarkText
import fr.equipe8.projetinfomobile.ui.theme.DisableRoutineCardColor
import fr.equipe8.projetinfomobile.ui.theme.RoutineCardColor
import fr.equipe8.projetinfomobile.ui.theme.SecondaryText
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun RoutineCard(routine: RoutineVM, onClick: () -> Unit, onActive: () -> Unit) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(if (routine.isActive) 8.dp else 0.dp),
        colors = CardDefaults.cardColors(if (routine.isActive)  RoutineCardColor else DisableRoutineCardColor)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = routine.name,
                    style = typography.titleLarge,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
                Text(
                    text = "%02d:%02d".format(routine.hour, routine.minute),
                    textAlign = TextAlign.End,
                    style = typography.titleLarge,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,

                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = routine.description,
                style = typography.titleMedium,
                maxLines = 3,
                color = SecondaryText,
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {


                Button(
                    onClick = {
                        onActive()
                    },
                    modifier = Modifier.size(30.dp),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                            containerColor = if (routine.isActive) MaterialTheme.colorScheme.primary else Color(0xFF363636))
                ) {
                    Icon(
                        painter = painterResource(id = if (routine.isActive) R.drawable.routine_active else R.drawable.routine_inactive), contentDescription = "Activer/Désactiver")
                }
                if(routine.periodicity is PeriodOptions.CustomDays) {
                    Text(
                        text = routine.daysOfWeek.joinToString(", ") {
                            it.getDisplayName(
                                TextStyle.SHORT,
                                Locale.FRANCE
                            )
                        },
                        textAlign = TextAlign.Right,
                        style = typography.titleSmall,
                        maxLines = 1
                    )
                }
                else if(routine.periodicity is PeriodOptions.AllDays) {
                    Text(
                        text = "Chaque Jours",
                        textAlign = TextAlign.Right,
                        style = typography.titleSmall,
                        maxLines = 1
                    )
                }
            }
        }
    }
}


