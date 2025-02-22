package fr.equipe8.projetinfomobile.data.routines

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(routine: Routine)

    @Query("SELECT * FROM routines")
    suspend fun getAllRoutines(): List<Routine>

    @Query("SELECT * FROM routines WHERE id = :taskId")
    suspend fun getRoutineById(taskId: Long): Routine?

    @Update
    suspend fun update(routine: Routine)

    @Delete
    suspend fun delete(routine: Routine)
}