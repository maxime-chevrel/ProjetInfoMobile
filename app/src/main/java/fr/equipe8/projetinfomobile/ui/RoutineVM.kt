package fr.equipe8.projetinfomobile.ui

import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.toBitmask
import fr.equipe8.projetinfomobile.toDayOfWeekSet
import fr.equipe8.projetinfomobile.ui.addeditscreen.PeriodOptions
import java.time.DayOfWeek

data class RoutineVM (
    val id: Int = 0,
    val name :String = "",
    val description : String = "",
    val hour: Byte = 0,
    val minute: Byte = 0,
    val daysOfWeek: Set<DayOfWeek> = emptySet(),
    var isActive : Boolean = true,
    var periodicity: PeriodOptions = PeriodOptions.NoDays
) {
    companion object {
        fun fromEntity(entity: Routine): RoutineVM {
            return RoutineVM(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                hour = entity.hour,
                minute = entity.minute,
                daysOfWeek = entity.daysOfWeek.toDayOfWeekSet(),
                isActive = entity.isActive,
                periodicity = PeriodOptions.getTab()[entity.periodicity]
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
        minute = this.minute,
        daysOfWeek = this.daysOfWeek.toBitmask(),
        isActive = this.isActive,
        periodicity = this.periodicity.id
    )
}