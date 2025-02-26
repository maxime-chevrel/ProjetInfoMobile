package fr.equipe8.projetinfomobile.ui.routinescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.equipe8.projetinfomobile.R
import fr.equipe8.projetinfomobile.navigation.ScreenRoute
import fr.equipe8.projetinfomobile.viewmodels.RoutinesListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesListScreen(navController: NavController, viewModel: RoutinesListViewModel, returnAction:Int = 0) {
    val routines = viewModel.routines.collectAsState()

    val sfFontFamily = FontFamily(
        Font(R.font.sf, FontWeight.Normal)
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        when (returnAction) {
            1 -> scope.launch {
                snackbarHostState.showSnackbar("Routine ajoutée avec succès") }

            2 -> scope.launch {
                snackbarHostState.showSnackbar("Routine modifiée avec succès") }

            3 -> scope.launch {
                snackbarHostState.showSnackbar("Routine supprimée avec succès") }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton (onClick = {
                navController.navigate(ScreenRoute.AddEditRoutineScreen.route+"?routineId=-1") //-1 = New routine
            }) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Ajouter une Routine")
            }
    },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        topBar = {
            Column {
                // Pour afficher si on modifie/ajoute
                TopAppBar(
                    title = {
                        Text(
                            "Routines",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            style = TextStyle(
                                fontSize = 36.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = sfFontFamily
                            )
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
        ) {contentPadding->
            LazyColumn(modifier = Modifier.padding(contentPadding)) {
                itemsIndexed(routines.value) { _, routine ->
                    RoutineCard(routine
                    ) {
                        navController.navigate(ScreenRoute.AddEditRoutineScreen.route + "?routineId=${routine.id}")
                    }
                }
            }
        }

    }