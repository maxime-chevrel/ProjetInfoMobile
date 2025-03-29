package fr.equipe8.projetinfomobile.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.equipe8.projetinfomobile.ui.RoutineVM
import fr.equipe8.projetinfomobile.usecases.RoutinesUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RoutinesListViewModel @Inject constructor(
    private val routinesUseCases: RoutinesUseCases,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {
    val returnAction = savedStateHandle.get<Int>("returnAction") ?: 0

    private val _routines = MutableStateFlow<List<RoutineVM>>(emptyList())
    val routines: StateFlow<List<RoutineVM>> get() = _routines
    private var job : Job? = null
    init {
        fetchRoutines()
    }

    //Getting all the routines
    private fun fetchRoutines() {
        job?.cancel()
        job = routinesUseCases.getAllRoutines.invoke().onEach { routines ->
            _routines.value = routines.map {
                RoutineVM.fromEntity(it)
            }
        }.launchIn(viewModelScope)
    }
}

