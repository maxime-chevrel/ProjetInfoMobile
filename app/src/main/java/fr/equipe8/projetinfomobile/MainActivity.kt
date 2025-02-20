package fr.equipe8.projetinfomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import fr.equipe8.projetinfomobile.ui.addeditscreen.AddEditScreen
import fr.equipe8.projetinfomobile.ui.routinescreen.RoutineScreen
import fr.equipe8.projetinfomobile.viewmodels.RoutineScreenViewModel
import fr.equipe8.projetinfomobile.ui.theme.ProjetInfoMobileTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProjetInfoMobileTheme {
                App()
            }
        }
    }
}


@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "routineScreen"
    ) {
        composable("routineScreen") {
            val viewModel: RoutineScreenViewModel = hiltViewModel()
            RoutineScreen(navController, viewModel)
        }
        composable("addEditRoutineScreen?routineId={routineId}",
            arguments = listOf(
                navArgument(name = "routineId") {
                    type = NavType.LongType
                    defaultValue=-1
                }
            )) {navBackStackEntry ->
            val routineId = navBackStackEntry.arguments?.getLong("routineId") ?: -1
            AddEditScreen(navController, routineId)
        }
    }
}