package fr.equipe8.projetinfomobile

import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeDataBase : RoutineDao
{
    val routines = mutableListOf<Routine>()

    override suspend fun insert(routine: Routine): Long {
        if (routines.contains(routine)) {
            return routines.indexOf(routine).toLong()
        } else {
            routines.add(routine)
            return routines.indexOf(routine).toLong()
        }
    }
    override fun getAllRoutines(): Flow<List<Routine>> {
        return flow<List<Routine>> {
            emit(routines)
        }
    }

    override fun getRoutineById(taskId: Int): Routine? {
        return routines.find { it.id == taskId }
    }

    override suspend fun delete(routine: Routine) {
        routines.remove(routine)
    }
}