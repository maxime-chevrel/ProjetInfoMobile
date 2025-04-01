package fr.equipe8.projetinfomobile.ui.routinescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fr.equipe8.projetinfomobile.navigation.ScreenRoute
import fr.equipe8.projetinfomobile.viewmodels.RoutinesListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesListScreen(navController: NavController, viewModel: RoutinesListViewModel= hiltViewModel()) {
    val routines = viewModel.routines.collectAsState()



    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        when (viewModel.returnAction) {
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
            if(routines.value.isNotEmpty()) {
                FloatingActionButton(onClick = {
                    navController.navigate(ScreenRoute.AddEditRoutineScreen.route + "?routineId=-1") //-1 = New routine
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Ajouter une Routine"
                    )
                }
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
                                fontWeight = FontWeight.Bold,

                                //fontFamily = sfFontFamily
                            )
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.shadow(10.dp)
                )
            }
        }
        ) {contentPadding->
        Column {
            Spacer(modifier = Modifier.height(16.dp))

            if (routines.value.isEmpty()) {
                EmptyListScreen(
                    navController = navController,
                    modifier = Modifier.padding(contentPadding)
                )
            }else{
                LazyColumn(modifier = Modifier.padding(contentPadding)) {
                    itemsIndexed(routines.value) { _, routine ->
                        RoutineCard(
                            routine
                        ) {
                            navController.navigate(ScreenRoute.AddEditRoutineScreen.route + "?routineId=${routine.id}")
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.padding(50.dp))
                    }
                }
            }
        }

    }
}



@Composable
fun EmptyListScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Aucune routine trouvée",
                style = TextStyle(
                    fontSize = 33.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,

                )
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    navController.navigate("${ScreenRoute.AddEditRoutineScreen.route}?routineId=-1")
                },
                shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth().shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp)).clip(
                    RoundedCornerShape(12.dp))
            ) {
                Icon(
                    Icons.Default.AddCircle,
                    contentDescription = "Premiere routine",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = "Créer ma première routine",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

            }
        }
    }
}