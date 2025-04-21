package fr.equipe8.projetinfomobile.ui.routinescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import fr.equipe8.projetinfomobile.ui.RoutineVM
import fr.equipe8.projetinfomobile.ui.theme.DisableRoutineCardColor
import fr.equipe8.projetinfomobile.ui.theme.RoutineCardColor
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class RoutineCardTest{

    private val routine = RoutineVM(1, "Test Routine", "Description", 10.toByte(), 25.toByte(), setOf(), true)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun boutonActiverDesactiverChangeCouleur() {
        composeTestRule.setContent {
            MaterialTheme {
                val isActive = remember { mutableStateOf(true) }
                val currentRoutine = routine.copy(isActive = isActive.value)
                Box(modifier = Modifier.testTag("routine_card")) {
                    RoutineCard(
                        routine = currentRoutine,
                        onClick = { },
                        onActive = { isActive.value = false }
                    )
                }
            }
        }

        composeTestRule.waitForIdle()

        //on recup l'image de la routine pour vérifier la couleur de base
        val imageActive = composeTestRule.onNodeWithTag("routine_card").captureToImage()
        val bitmapActive = imageActive.asAndroidBitmap()
        val centerX = bitmapActive.width / 2
        val centerY = bitmapActive.height / 2
        val initialColor = bitmapActive.getPixel(centerX, centerY)
        assertEquals(RoutineCardColor.toArgb(), initialColor)


        composeTestRule.onNodeWithContentDescription("Activer/Désactiver").performClick()
        composeTestRule.waitForIdle()

        val imageInactive = composeTestRule.onNodeWithTag("routine_card").captureToImage()
        val bitmapInactive = imageInactive.asAndroidBitmap()
        val updatedColor = bitmapInactive.getPixel(centerX, centerY)
        assertEquals(DisableRoutineCardColor.toArgb(), updatedColor)
    }
}