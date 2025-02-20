package fr.equipe8.projetinfomobile.data.routines

import android.content.Context

class RoutineRepository (private val dao: RoutineDao) {
    //Fast external functions
    suspend fun getRoutines(): List<Routine> = dao.getAllRoutines()
    suspend fun getRoutineById(routineId: Long): Routine? = dao.getRoutineById(routineId)
    suspend fun addRoutine(routine: Routine) = dao.insert(routine)
    suspend fun updateRoutine(routine: Routine) = dao.update(routine)
    suspend fun removeRoutine(routine: Routine) = dao.delete(routine)

    //Internal functions
}