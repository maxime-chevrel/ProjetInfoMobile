package fr.equipe8.projetinfomobile.usecases

data class RoutinesUseCases(
    val getAllRoutines: GetAllRoutinesUseCase,
    val getRoutineById: GetRoutineByIdUseCase,
    val upsertRoutine: UpsertRoutineUseCase,
    val deleteRoutine: DeleteRoutineUseCase
)
