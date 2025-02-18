package fr.equipe8.projetinfomobile.data

val routines : List<Routine> = listOf(
    Routine("r1","d1",1,1),
    Routine("r2","d2",2,2),
    Routine("r3","d3",3,3)
)

fun getRoutines() : List<Routine>
{
    return routines
}