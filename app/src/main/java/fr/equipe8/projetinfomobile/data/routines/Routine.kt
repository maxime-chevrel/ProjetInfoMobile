package fr.equipe8.projetinfomobile.data.routines

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
    data class Routine(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val name :String = "",
        val description : String = "",
        val hour: Byte = 0,
        val minute: Byte = 0,
        val daysOfWeek: Byte = 0,
        val isActive: Boolean = true,
        val periodicity: Int = 0,
    )