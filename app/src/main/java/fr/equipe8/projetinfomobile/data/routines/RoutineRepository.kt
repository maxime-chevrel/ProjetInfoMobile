package fr.equipe8.projetinfomobile.data.routines

import kotlinx.coroutines.flow.Flow

class RoutineRepository (private val dao: RoutineDao) {
    //Fast external functions
    fun getAllRoutines(): Flow<List<Routine>> = dao.getAllRoutines()
    fun getRoutineById(routineId: Int): Routine? = dao.getRoutineById(routineId)
    suspend fun upsertRoutine(routine: Routine) = dao.insert(routine)
    suspend fun removeRoutine(routine: Routine) = dao.delete(routine)

    //Internal functions
}