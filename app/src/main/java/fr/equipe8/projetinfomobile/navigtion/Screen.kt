package fr.equipe8.projetinfomobile.navigtion

sealed class Screen(val route: String) {
    data object ListScreen :Screen(route = "list_screen")
}