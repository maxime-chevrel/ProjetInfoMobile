package fr.equipe8.projetinfomobile.usecases

import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository

class GetRoutineByIdUseCase(private val repository: RoutineRepository) {
    operator fun invoke(id : Long): Routine? {
        return repository.getRoutineById(id)
    }
}