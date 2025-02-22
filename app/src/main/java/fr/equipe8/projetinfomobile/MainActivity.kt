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
import fr.equipe8.projetinfomobile.navigation.ScreenRoute
import fr.equipe8.projetinfomobile.ui.addeditscreen.AddEditRoutineScreen
import fr.equipe8.projetinfomobile.ui.routinescreen.RoutinesListScreen
import fr.equipe8.projetinfomobile.viewmodels.RoutinesListViewModel
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
        startDestination = ScreenRoute.RoutinesListScreen.route
    ) {
        composable(ScreenRoute.RoutinesListScreen.route) {
            val viewModel: RoutinesListViewModel = hiltViewModel()
            RoutinesListScreen(navController, viewModel)
        }
        composable(ScreenRoute.AddEditRoutineScreen.route+"?routineId={routineId}",
            arguments = listOf(
                navArgument(name = "routineId") {
                    type = NavType.LongType
                    defaultValue=-1
                }
            )) {navBackStackEntry ->
            val routineId = navBackStackEntry.arguments?.getLong("routineId") ?: -1
            AddEditRoutineScreen(navController, routineId)
        }
    }
}