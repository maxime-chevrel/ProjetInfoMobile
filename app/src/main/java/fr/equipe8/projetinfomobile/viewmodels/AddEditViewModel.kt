package fr.equipe8.projetinfomobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: RoutineRepository
) : ViewModel() {
    private val _routine = MutableStateFlow(Routine())
    val routine: StateFlow<Routine> get() = _routine

    private val _isRoutineEdited = MutableStateFlow(false)
    val isRoutineEdited: StateFlow<Boolean> get() = _isRoutineEdited

    fun getRoutineById(routineId: Long?) {
        viewModelScope.launch {
            _routine.value = if (routineId != null && routineId != -1L) {
                repository.getRoutineById(routineId) ?: Routine()
            } else {
                Routine()
            }
        }
    }

    fun onRoutineChanged(routine: Routine) {
        _routine.value = routine
        _isRoutineEdited.value = true
    }

    fun saveRoutine() {
        if (_isRoutineEdited.value) {
            viewModelScope.launch {
                repository.updateRoutine(_routine.value)
            }
        }
    }

    fun addRoutine() {
        viewModelScope.launch {
            repository.addRoutine(_routine.value)
        }
    }
}