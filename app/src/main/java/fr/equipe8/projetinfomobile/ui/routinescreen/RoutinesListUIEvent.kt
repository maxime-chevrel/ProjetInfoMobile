package fr.equipe8.projetinfomobile.ui.routinescreen

sealed interface RoutinesListUiEvent {
    data class ShowMessage(val message: String) : RoutinesListUiEvent
    data class Navigate(val id : Int) : RoutinesListUiEvent
}