package fr.equipe8.projetinfomobile.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.equipe8.projetinfomobile.data.routines.RoutineDatabase
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository
import fr.equipe8.projetinfomobile.usecases.CancelRoutineNotificationUseCase
import fr.equipe8.projetinfomobile.usecases.DeleteRoutineUseCase
import fr.equipe8.projetinfomobile.usecases.GetAllRoutinesUseCase
import fr.equipe8.projetinfomobile.usecases.GetRoutineByIdUseCase
import fr.equipe8.projetinfomobile.usecases.RoutinesUseCases
import fr.equipe8.projetinfomobile.usecases.ScheduleRoutineNotificationUseCase
import fr.equipe8.projetinfomobile.usecases.ShareUseCase
import fr.equipe8.projetinfomobile.usecases.UpsertRoutineUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    @Singleton
    fun provideRoutineRepository(routineDatabase: RoutineDatabase): RoutineRepository {
        return RoutineRepository(routineDatabase.routineDao())
    }

    @Provides
    @Singleton
    fun provideRoutinesUseCases(@ApplicationContext context: Context, routineRepository: RoutineRepository): RoutinesUseCases {
        return RoutinesUseCases(
            getAllRoutines = GetAllRoutinesUseCase(routineRepository),
            getRoutineById = GetRoutineByIdUseCase(routineRepository),
            upsertRoutine = UpsertRoutineUseCase(routineRepository),
            deleteRoutine = DeleteRoutineUseCase(routineRepository),
            scheduleRoutineNotification = ScheduleRoutineNotificationUseCase(context),
            cancelRoutineNotification = CancelRoutineNotificationUseCase(context),
            shareRoutine = ShareUseCase()
        )
    }
}
