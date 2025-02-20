package fr.equipe8.projetinfomobile.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.equipe8.projetinfomobile.data.routines.RoutineDao
import fr.equipe8.projetinfomobile.data.routines.RoutineDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoutineDatabase {
        return Room.databaseBuilder(
            context,
            RoutineDatabase::class.java,
            "routine_database"
        ).build()
    }

    @Provides
    fun provideRoutineDao(db: RoutineDatabase): RoutineDao {
        return db.routineDao()
    }
}
