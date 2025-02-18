package fr.equipe8.projetinfomobile.data

var idn =0

data class Routine(
    val name :String,
    val description : String,
    val hour: Int,
    val minute: Int,
    val id: Int = idn++
)