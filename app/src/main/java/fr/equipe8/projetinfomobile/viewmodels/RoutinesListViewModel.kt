package fr.equipe8.projetinfomobile.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.equipe8.projetinfomobile.notifications.NotificationsHelper
import fr.equipe8.projetinfomobile.ui.RoutineVM
import fr.equipe8.projetinfomobile.ui.routinescreen.RoutinesListEvent
import fr.equipe8.projetinfomobile.ui.routinescreen.RoutinesListUiEvent
import fr.equipe8.projetinfomobile.usecases.RoutinesUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutinesListViewModel @Inject constructor(
    private val routinesUseCases: RoutinesUseCases,
    private val notificationsHelper: NotificationsHelper,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _routines = MutableStateFlow<List<RoutineVM>>(emptyList())
    val routines: StateFlow<List<RoutineVM>> get() = _routines


    private val _eventFlow = MutableSharedFlow<RoutinesListUiEvent>(replay = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    private var job : Job? = null


    init {
        fetchRoutines()
        val returnAction = savedStateHandle.get<Int>("returnAction") ?: 0
        viewModelScope.launch {
            Log.d("RoutinesListViewModel", "Return action: $returnAction")
            when (returnAction) {
                1 -> _eventFlow.emit(RoutinesListUiEvent.ShowMessage("Routine ajoutée avec succès"))
                2 -> _eventFlow.emit(RoutinesListUiEvent.ShowMessage("Routine modifiée avec succès"))
                3 -> _eventFlow.emit(RoutinesListUiEvent.ShowMessage("Routine supprimée avec succès"))
                else -> {
                    // No action needed
                }
            }
        }
    }

    //Getting all the routines
    private fun fetchRoutines() {
        job?.cancel()
        job = routinesUseCases.getAllRoutines().onEach { routines ->
            _routines.value = routines.map {
                RoutineVM.fromEntity(it)
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: RoutinesListEvent) {
        when (event) {
            is RoutinesListEvent.RoutineClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(RoutinesListUiEvent.Navigate(event.id))
                }
            }
            is RoutinesListEvent.NewRoutine -> {
                viewModelScope.launch {
                    _eventFlow.emit(RoutinesListUiEvent.Navigate(-1))
                }
            }

            is RoutinesListEvent.ActiveRoutineClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    var routine = routinesUseCases.getRoutineById(event.id)
                    routine?.let {
                        routinesUseCases.upsertRoutine(it.copy(isActive = !it.isActive))
                        routine = it.copy(isActive = !it.isActive)
                        if (it.isActive) {
                            routinesUseCases.cancelRoutineNotification(RoutineVM.fromEntity(routine!!))
                        } else {
                            routinesUseCases.scheduleRoutineNotification(RoutineVM.fromEntity(routine!!))
                        }
                        _eventFlow.emit(RoutinesListUiEvent.ShowMessage("Routine ${if (it.isActive) "désactivée" else "activée"}"))
                        fetchRoutines()

                    }
                }
            }

            is RoutinesListEvent.DebugMessage -> {
                viewModelScope.launch {
                    _eventFlow.emit(RoutinesListUiEvent.ShowMessage("Debug message"))
                }
            }
        }
    }
}

