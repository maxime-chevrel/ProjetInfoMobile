package fr.equipe8.projetinfomobile.data.routines

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Upsert
    suspend fun insert(routine: Routine) : Long

    @Query("SELECT * FROM routines ORDER BY hour, minute, name")
    fun getAllRoutines(): Flow<List<Routine>>

    @Query("SELECT * FROM routines WHERE id = :taskId")
    fun getRoutineById(taskId: Int): Routine?

    @Delete
    suspend fun delete(routine: Routine)
}