package fr.equipe8.projetinfomobile.ui.addeditscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import fr.equipe8.projetinfomobile.ui.RoutineVM
import org.junit.Rule
import org.junit.Test

@Composable
fun TestAddEditRoutineScreen(routineState: MutableState<RoutineVM>) {
    Scaffold(
        floatingActionButton = {
            if (routineState.value.id != 0) {
                Button(
                    onClick = { routineState.value = RoutineVM() },
                    modifier = Modifier.testTag("delete_button")
                ) {
                    Text("Supprimer")
                }
            }
        }
    ) { contentPadding ->
        if (routineState.value.id == 0) {
            // Écran vide pour simmuler l'ecran principale
            Box(
                modifier = Modifier
                    .testTag("empty_screen")
                    .padding(contentPadding)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Aucune routine trouvée")
            }
        } else {
            // Ecran modif routine pour simuler
            Column(
                modifier = Modifier
                    .testTag("detail_screen")
                    .padding(contentPadding)
                    .fillMaxWidth()
            ) {
                Text(text = routineState.value.name, modifier = Modifier.testTag("routine_name"))
                Text(text = routineState.value.description, modifier = Modifier.testTag("routine_description"))
            }
        }
    }
}

class AddEditRoutineScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun supprimerRoutineModif() {

        val routineState = mutableStateOf(RoutineVM(1, "Test Routine", "Description"))


        composeTestRule.setContent {
            MaterialTheme {
                TestAddEditRoutineScreen(routineState = routineState)
            }
        }
        composeTestRule.waitForIdle()

        // Vérifier que l'écran modif est visible et que le bouton supprimer est present
        composeTestRule.onNodeWithTag("detail_screen").assertExists()
        composeTestRule.onNodeWithTag("delete_button").assertExists()

        // Clic pour supp le bouton
        composeTestRule.onNodeWithTag("delete_button").performClick()
        composeTestRule.waitForIdle()

        // Si supprime, l'ecran vide est visible
        composeTestRule.onNodeWithTag("empty_screen").assertExists()
        composeTestRule.onNodeWithTag("detail_screen").assertDoesNotExist()
    }
}