package fr.equipe8.projetinfomobile.ui.routinescreen

sealed class RoutinesListEvent {
    data class RoutineClicked(val id : Long) : RoutinesListEvent()
    data class ActiveRoutineClicked(val id : Long) : RoutinesListEvent()
    data object NewRoutine : RoutinesListEvent()
    data object  DebugMessage : RoutinesListEvent()
}