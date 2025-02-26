package fr.equipe8.projetinfomobile.ui

import androidx.room.PrimaryKey
import fr.equipe8.projetinfomobile.data.routines.Routine

data class RoutineVM (
    val id: Int = 0,
    val name :String = "",
    val description : String = "",
    val hour: Int = 0,
    val minute: Int = 0
) {
    companion object {
        fun fromEntity(entity: Routine): RoutineVM {
            return RoutineVM(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                hour = entity.hour,
                minute = entity.minute
            )
        }
    }
}

fun RoutineVM.toEntity(): Routine {
    return Routine(
        id = this.id,
        name = this.name,
        description = this.description,
        hour = this.hour,
        minute = this.minute
    )
}