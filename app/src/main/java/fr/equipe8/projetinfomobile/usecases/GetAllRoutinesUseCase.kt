package fr.equipe8.projetinfomobile.usecases

import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository
import kotlinx.coroutines.flow.Flow

class GetAllRoutinesUseCase(private val routineRepository: RoutineRepository) {
    operator fun invoke(): Flow<List<Routine>> {
        return routineRepository.getAllRoutines()
    }

}