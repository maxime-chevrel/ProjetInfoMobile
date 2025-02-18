package fr.equipe8.projetinfomobile.presentation.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import fr.equipe8.projetinfomobile.data.Routine
import fr.equipe8.projetinfomobile.data.getRoutines

class ListScreenViewModel(): ViewModel() {
    private val _routines: MutableState<List<Routine>> = mutableStateOf(emptyList<Routine>())
    val routines = _routines

    init {
        _routines.value= getRoutines()
    }

}