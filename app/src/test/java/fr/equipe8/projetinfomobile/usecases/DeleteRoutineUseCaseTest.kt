package fr.equipe8.projetinfomobile.usecases

import fr.equipe8.projetinfomobile.FakeDataBase
import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class DeleteRoutineUseCaseTest {
    private lateinit var deleteRoutineUseCase: DeleteRoutineUseCase
    private val fakeDatabase = FakeDataBase()

    @Before
    fun setUp() {
        deleteRoutineUseCase = DeleteRoutineUseCase(RoutineRepository(fakeDatabase))
    }

    @Test
    fun `La suppression d'une routine doit fonctionner`() = runBlocking {
        val routine = Routine(
            id = 1,
            name = "Routine à supprimer",
            description = "Description",
            periodicity = 1
        )
        fakeDatabase.insert(routine)
        deleteRoutineUseCase(routine)

        val result = fakeDatabase.getRoutineById(routine.id)
        assertNull(result)
    }

}