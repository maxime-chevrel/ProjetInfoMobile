package fr.equipe8.projetinfomobile.data.routines

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class Routine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name :String = "",
    val description : String = "",
    val hour: Int = 0,
    val minute: Int = 0
)