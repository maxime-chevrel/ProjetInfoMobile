package fr.equipe8.projetinfomobile.usecases

import fr.equipe8.projetinfomobile.FakeDataBase
import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetRoutineByIdUseCaseTest {

    private lateinit var getRoutineByIdUseCase: GetRoutineByIdUseCase
    private val fakeDatabase = FakeDataBase()

    @Before
    fun setUp() {
        getRoutineByIdUseCase = GetRoutineByIdUseCase(RoutineRepository(fakeDatabase))
    }

    @Test
    fun `La récupération d'une routine par son ID doit fonctionner`() {
        runBlocking {
            val routine = Routine(
                id = 100,
                name = "Routine Test",
                description = "Description de test",
                periodicity = 1
            )
            fakeDatabase.insert(routine)

            val result = getRoutineByIdUseCase(100)
            assertNotNull(result)
        }
    }

    @Test
    fun `Si l'id n'existe pas, la récupération retourne null`() {
        val result = getRoutineByIdUseCase(999)
        assertNull(result)
    }
}