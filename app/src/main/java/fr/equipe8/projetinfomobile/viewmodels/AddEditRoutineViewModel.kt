package fr.equipe8.projetinfomobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository
import fr.equipe8.projetinfomobile.ui.RoutineVM
import fr.equipe8.projetinfomobile.ui.toEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRoutineViewModel @Inject constructor(
    private val repository: RoutineRepository
) : ViewModel() {
    private val _routine = MutableStateFlow(RoutineVM())
    val routine: StateFlow<RoutineVM> get() = _routine

    private val _isRoutineEdited = MutableStateFlow(false)
    val isRoutineEdited: StateFlow<Boolean> get() = _isRoutineEdited

    fun getRoutineById(routineId: Long?) {
        viewModelScope.launch {
            _routine.value = if (routineId != null && routineId != -1L) {
                repository.getRoutineById(routineId)?.let { RoutineVM.fromEntity(it) } ?: RoutineVM()
            } else {
                RoutineVM()
            }
        }
    }

    fun onRoutineChanged(routine: RoutineVM) {
        _routine.value = routine
        _isRoutineEdited.value = true
    }

    fun saveRoutine() {
        if (_isRoutineEdited.value) {
            viewModelScope.launch {
                repository.updateRoutine(_routine.value.toEntity())
            }
        }
    }

    fun addRoutine() {
        viewModelScope.launch {
            repository.addRoutine(_routine.value.toEntity())
        }
    }
    fun deleteRoutine() {
        viewModelScope.launch {
            repository.removeRoutine(_routine.value.toEntity())
        }
    }
}