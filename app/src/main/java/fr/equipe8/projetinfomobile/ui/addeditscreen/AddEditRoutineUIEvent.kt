package fr.equipe8.projetinfomobile.ui.addeditscreen

import fr.equipe8.projetinfomobile.data.routines.Routine

sealed interface AddEditRoutineUiEvent {
    data class ShowMessage(val message: String) : AddEditRoutineUiEvent
    data class SavedRoutine(val returnAction : Int) : AddEditRoutineUiEvent
    data class ShareRoutine(val routine: Routine) : AddEditRoutineUiEvent
}