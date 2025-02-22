package fr.equipe8.projetinfomobile.navigation

sealed class ScreenRoute(val route: String) {
    data object RoutinesListScreen: ScreenRoute(route = "routinesListScreen")
    data object AddEditRoutineScreen: ScreenRoute(route = "addEditRoutineScreen")
}