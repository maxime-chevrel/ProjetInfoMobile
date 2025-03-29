package fr.equipe8.projetinfomobile.ui.addeditscreen

sealed interface AddEditRoutineUiEvent {
    data class ShowMessage(val message: String) : AddEditRoutineUiEvent
    data class SavedStory(val returnAction : Int) : AddEditRoutineUiEvent
}