package fr.equipe8.projetinfomobile.data

val routines2 : MutableList<Routine> = mutableListOf(
    Routine("r128374850nd8rcyrnfunv","Ceci est la treas longue description de la routin numero 1",1,1),
    Routine("r2","Ceci est la treas longue description de la routin numero 2",2,2),
    Routine("r3","Ceci est la treas longue description de la routin numero 3",3,3)
)

fun getRoutines() : List<Routine>
{
    return routines2
}

fun addOrUpdate(routine: Routine){
    val find =   routines2.find { it.id==routine.id }
    find?.let { routines2.remove(it) }
    routines2.add(routine)
}