package fr.equipe8.projetinfomobile.usecases

import fr.equipe8.projetinfomobile.FakeDataBase
import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.data.routines.RoutineRepository
import fr.equipe8.projetinfomobile.ui.addeditscreen.RoutineAddException
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class UpsertRoutineUseCaseTest {
    lateinit var upsertRoutineUseCase: UpsertRoutineUseCase
    val dataBase = FakeDataBase()

    @Before
    fun setUp() {
        upsertRoutineUseCase = UpsertRoutineUseCase(RoutineRepository(dataBase))
    }



    @Test
    fun `L'insertion d'une routine doit fonctionner`() {
        runBlocking {
            val routine = Routine(
                id = 1,
                name = "Insert Routine",
                description = "InsertTest",
                periodicity = 0
            )
            upsertRoutineUseCase(routine)
        }
    }

    @Test(expected = RoutineAddException::class)
    fun `Doit renvoyer une erreur si le nom est vide`() {
        runBlocking {
            val routine = Routine(
                id = 2,
                name = "",
                description = "Routine Sans Nom",
                periodicity = 0
            )
            upsertRoutineUseCase(routine)
        }
    }

    @Test(expected = RoutineAddException::class)
    fun `Doit renvoyer une erreur si la periode est vide mais qu'aucun jour n'est choisi`() {
        runBlocking {
            val routine = Routine(id = 3, name = "Personnalisée", periodicity = 2, daysOfWeek = 0.toByte())

            upsertRoutineUseCase(routine)
        }
    }


}
