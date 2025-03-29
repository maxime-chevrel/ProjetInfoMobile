package fr.equipe8.projetinfomobile.usecases

import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository

class DeleteRoutineUseCase(private val routineRepository: RoutineRepository) {
    suspend operator fun invoke(routine: Routine) {
        routineRepository.removeRoutine(routine)
    }
}