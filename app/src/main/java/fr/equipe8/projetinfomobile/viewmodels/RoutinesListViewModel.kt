package fr.equipe8.projetinfomobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository
import fr.equipe8.projetinfomobile.ui.RoutineVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutinesListViewModel @Inject constructor(
    private val repository: RoutineRepository): ViewModel() {

    private val _routines = MutableStateFlow<List<RoutineVM>>(emptyList())
    val routines: StateFlow<List<RoutineVM>> get() = _routines

    init {//Load routines
        fetchRoutines()
    }

    //Getting all the routines
    private fun fetchRoutines() {
        viewModelScope.launch {
            _routines.value = repository.getRoutines().map { RoutineVM.fromEntity(it) }
        }
    }
}

