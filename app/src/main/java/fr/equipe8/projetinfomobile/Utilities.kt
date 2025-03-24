package fr.equipe8.projetinfomobile

import java.time.DayOfWeek

fun Byte.toDayOfWeekSet(): Set<DayOfWeek> {
    val days = mutableSetOf<DayOfWeek>()
    DayOfWeek.entries.forEachIndexed { index, day ->
        if ((this.toInt() and (1 shl index)) != 0) {
            days.add(day)
        }
    }
    return days
}

fun Set<DayOfWeek>.toBitmask(): Byte {
    var bitmask = 0
    this.forEach { day ->
        bitmask = bitmask or (1 shl (day.ordinal))
    }
    return bitmask.toByte()
}