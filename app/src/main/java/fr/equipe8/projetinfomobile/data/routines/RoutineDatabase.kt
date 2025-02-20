package fr.equipe8.projetinfomobile.data.routines

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Routine::class], version = 1, exportSchema = false)
abstract class RoutineDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao

    companion object {
        @Volatile
        private var INSTANCE: RoutineDatabase? = null
    }
}