package fr.equipe8.projetinfomobile.usecases

import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository
import fr.equipe8.projetinfomobile.ui.addeditscreen.RoutineAddException
import kotlin.jvm.Throws

class UpsertRoutineUseCase(private val routineRepository: RoutineRepository) {
    @Throws(RoutineAddException::class)
    suspend operator fun invoke(routine: Routine){
        if(routine.name.isBlank()){
            throw RoutineAddException("Un Nom est nécéssaire")
        }
        return routineRepository.upsertRoutine(routine)
    }
}