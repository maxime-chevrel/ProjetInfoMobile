package fr.equipe8.projetinfomobile.ui.addeditscreen

import java.time.DayOfWeek

sealed class AddEditRoutineEvent {
    data class EnteredName(val name: String) :AddEditRoutineEvent()
    data class EnteredDescription(val description: String): AddEditRoutineEvent()
    data class ModifiedDay(val day: DayOfWeek): AddEditRoutineEvent()
    data class ModifiedTime(val hour: Byte, val minute : Byte): AddEditRoutineEvent()
    data object SaveRoutine: AddEditRoutineEvent()
    data object DeleteRoutine: AddEditRoutineEvent()
    data object ModifiedRepetition: AddEditRoutineEvent()
}