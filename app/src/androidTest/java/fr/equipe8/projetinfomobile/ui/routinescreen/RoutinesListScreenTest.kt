package fr.equipe8.projetinfomobile.ui.routinescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import fr.equipe8.projetinfomobile.data.routines.Routine
import org.junit.Rule
import org.junit.Test

@Composable
fun EmptyListScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("empty_list_screen"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Aucune routine trouvée",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun TestRoutinesScreen(routines: List<Routine>) {
    if (routines.isEmpty()) {
        EmptyListScreen()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("routines_list"),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(routines) { routine ->
                Text(text = routine.name)
            }
        }
    }
}


class RoutinesListScreenTest  {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun changementEtatListeRoutine() {
        val routinesState = mutableStateOf<List<Routine>>(emptyList())

        composeTestRule.setContent {
            MaterialTheme {
                TestRoutinesScreen(routines = routinesState.value)
            }
        }

        //On regarde si on est bien sur l'écran vide
        composeTestRule.onNodeWithTag("empty_list_screen").assertExists()
        composeTestRule.onNodeWithTag("routines_list").assertDoesNotExist()

        // Quand on ajoute une routine, l'UI doit être mise à jour
        val routine = Routine(id = 1, name = "Ma Routine", description = "Description")
        composeTestRule.runOnUiThread {
            routinesState.value = listOf(routine)
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("routines_list").assertExists()
        composeTestRule.onNodeWithTag("empty_list_screen").assertDoesNotExist()

        // Quand on supprime la derniere routine, l'UI doit être mise à jour sur l'ecrna vide
        composeTestRule.runOnUiThread {
            routinesState.value = emptyList()
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("empty_list_screen").assertExists()
        composeTestRule.onNodeWithTag("routines_list").assertDoesNotExist()
    }
}