package fr.equipe8.projetinfomobile.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.equipe8.projetinfomobile.ui.RoutineVM
import fr.equipe8.projetinfomobile.ui.addeditscreen.AddEditRoutineUiEvent
import fr.equipe8.projetinfomobile.ui.addeditscreen.AddEditRoutineEvent
import fr.equipe8.projetinfomobile.ui.addeditscreen.RoutineAddException
import fr.equipe8.projetinfomobile.ui.toEntity
import fr.equipe8.projetinfomobile.usecases.RoutinesUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRoutineViewModel @Inject constructor(
    val routinesUseCases: RoutinesUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val routineId :Int = savedStateHandle.get<Int>("routineId") ?: -1

    private val _routine = MutableStateFlow(RoutineVM())
    val routine: StateFlow<RoutineVM> get() = _routine

    private var isRoutineEdited =false

    private val _eventFlow = MutableSharedFlow<AddEditRoutineUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val routineEntity = routinesUseCases.getRoutineById(routineId)
            _routine.value = routineEntity?.let { RoutineVM.fromEntity(it) } ?: RoutineVM()
        }
    }

    fun onEvent(event : AddEditRoutineEvent) {
        when (event) {
            is AddEditRoutineEvent.EnteredName -> {
                _routine.value = _routine.value.copy(name = event.name)
                isRoutineEdited=true
            }

            is AddEditRoutineEvent.EnteredDescription -> {
                _routine.value = _routine.value.copy(description = event.description)
                isRoutineEdited=true

            }

            is AddEditRoutineEvent.ModifiedDay -> {
                val daysOfWeek = _routine.value.daysOfWeek
                if (daysOfWeek.contains(event.day)) {
                    _routine.value = _routine.value.copy(daysOfWeek = daysOfWeek - event.day)
                } else {
                    _routine.value = _routine.value.copy(daysOfWeek = daysOfWeek + event.day)
                }
                isRoutineEdited=true

            }

            is AddEditRoutineEvent.SaveRoutine -> {
                if (isRoutineEdited||routineId==-1) {
                    viewModelScope.launch {
                        try {
                            val upsertResult = routinesUseCases.upsertRoutine(_routine.value.toEntity())
                            val rId : Int= if (routineId == -1) upsertResult else routineId

                            _routine.value = _routine.value.copy(id = rId)

                            routinesUseCases.scheduleRoutineNotification(_routine.value)

                            if (routineId == -1) {
                                _eventFlow.emit(AddEditRoutineUiEvent.SavedRoutine(1))
                            } else {
                                _eventFlow.emit(AddEditRoutineUiEvent.SavedRoutine(2))
                            }
                        } catch (e: RoutineAddException) {
                            _eventFlow.emit(AddEditRoutineUiEvent.ShowMessage(e.message!!))
                        }
                    }
                } else {
                    viewModelScope.launch {
                        _eventFlow.emit(AddEditRoutineUiEvent.SavedRoutine(0))
                    }
                }
            }
            is AddEditRoutineEvent.DeleteRoutine -> {
                viewModelScope.launch {
                    routinesUseCases.deleteRoutine(_routine.value.toEntity())
                    routinesUseCases.cancelRoutineNotification(_routine.value)
                    _eventFlow.emit(AddEditRoutineUiEvent.SavedRoutine(3))
                }
            }
            is AddEditRoutineEvent.ModifiedTime -> {
                _routine.value = _routine.value.copy(hour = event.hour, minute = event.minute)
                isRoutineEdited=true

            }
            is AddEditRoutineEvent.ModifiedActive -> {
                _routine.value= _routine.value.copy(isActive = !_routine.value.isActive)
                isRoutineEdited=true
            }
            is AddEditRoutineEvent.ModifiedPeriodicity -> {
                _routine.value = _routine.value.copy(periodicity = event.periodOptions)
                isRoutineEdited=true
            }
            is AddEditRoutineEvent.ShareRoutine ->{
                viewModelScope.launch {
                    _eventFlow.emit(AddEditRoutineUiEvent.ShareRoutine(_routine.value.toEntity()))
                }
            }
        }
    }
}