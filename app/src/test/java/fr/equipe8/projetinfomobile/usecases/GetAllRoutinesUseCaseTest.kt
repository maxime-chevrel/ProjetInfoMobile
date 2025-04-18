package fr.equipe8.projetinfomobile.usecases

import fr.equipe8.projetinfomobile.FakeDataBase
import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetAllRoutinesUseCaseTest {
    private lateinit var getAllRoutinesUseCase: GetAllRoutinesUseCase
    private val fakeDatabase = FakeDataBase()

    @Before
    fun setUp() {
        getAllRoutinesUseCase = GetAllRoutinesUseCase(RoutineRepository(fakeDatabase))
    }

    @Test
    fun `L'obtention de toutes les routines doit fonctionner`() = runBlocking {

        val routine1 = Routine(
            id = 1,
            name = "Routine 1",
            description = "Première routine",
            periodicity = 1
        )
        val routine2 = Routine(
            id = 2,
            name = "Routine 2",
            description = "Deuxième routine",
            periodicity = 2
        )

        fakeDatabase.insert(routine1)
        fakeDatabase.insert(routine2)

        val routines = getAllRoutinesUseCase().first()

        assertEquals(2, routines.size)
        assertTrue(routines.contains(routine1))
        assertTrue(routines.contains(routine2))
    }
}