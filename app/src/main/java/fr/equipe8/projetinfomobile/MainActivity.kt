package fr.equipe8.projetinfomobile

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import fr.equipe8.projetinfomobile.navigation.ScreenRoute
import fr.equipe8.projetinfomobile.ui.addeditscreen.AddEditRoutineScreen
import fr.equipe8.projetinfomobile.ui.routinescreen.RoutinesListScreen
import fr.equipe8.projetinfomobile.ui.theme.ProjetInfoMobileTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(null, "Permission accordée")
        } else {
            Log.d(null, "Permission refusée")
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                // Cas 1: Permission déjà accordée
                ContextCompat.checkSelfPermission(
                    this, POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> { // Permission déjà accordée
                }
                // Cas 2: L'utilisateur a déjà refusé, montrer explication
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, POST_NOTIFICATIONS
                ) -> {
                    // Afficher un dialogue expliquant pourquoi
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
                // Cas 3: Première demande ou "Ne plus demander" non coché
                else -> {
                    // Demander la permission directement
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            ProjetInfoMobileTheme {
                val routineId = intent.getIntExtra("routineId", -1)
                Log.d( "MainActivity", "routineId from intent : $routineId")
                App(routineId)
            }
        }
    }
}


@Composable
fun App(id :Int = -1) {
    val navController = rememberNavController()

    val start = if (id == -1) {
        ScreenRoute.RoutinesListScreen.route
    } else {
        ScreenRoute.AddEditRoutineScreen.route + "?routineId=${id}"
    }
    Log.d( "MainActivity", "start: $start, $id")
    NavHost(navController = navController,
        startDestination = start
    ) {
        composable(route=ScreenRoute.RoutinesListScreen.route+"?returnAction={returnAction}",
            arguments = listOf(
                navArgument(name = "returnAction") {
                    type = NavType.IntType
                    defaultValue=0
                }
            )){ _ ->
            RoutinesListScreen(navController)
        }
        composable(ScreenRoute.AddEditRoutineScreen.route+"?routineId={routineId}",
            arguments = listOf(
                navArgument(name = "routineId") {
                    type = NavType.IntType
                    defaultValue=-1
                }
            )) { _ ->
            AddEditRoutineScreen(navController)
        }
    }
}