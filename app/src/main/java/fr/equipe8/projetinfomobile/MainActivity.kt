package fr.equipe8.projetinfomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.equipe8.projetinfomobile.navigtion.Screen
import fr.equipe8.projetinfomobile.presentation.list.ListScreen
import fr.equipe8.projetinfomobile.presentation.list.ListScreenViewModel
import fr.equipe8.projetinfomobile.ui.theme.ProjetInfoMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetInfoMobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.ListScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable(Screen.ListScreen.route) {
                            val stories = viewModel<ListScreenViewModel>()
                            ListScreen(navController = navController, viewModel = stories)
                        }

                    }
                }
            }
        }
    }
}
