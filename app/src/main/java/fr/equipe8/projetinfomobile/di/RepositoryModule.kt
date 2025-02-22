package fr.equipe8.projetinfomobile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import fr.equipe8.projetinfomobile.data.routines.RoutineDao
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideRoutineRepository(routineDao: RoutineDao): RoutineRepository {
        return RoutineRepository(routineDao)
    }
}
