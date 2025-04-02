package fr.equipe8.projetinfomobile.ui.addeditscreen

sealed class PeriodOptions(val name : String , val id: Int) {
    data object NoDays: PeriodOptions( name = "Aucun", id = 0)
    data object AllDays: PeriodOptions( name = "Tous les jours", id = 1)
    data object CustomDays: PeriodOptions( name = "Personnalisé...", id = 2)
    companion object{
        fun getTab(): List<PeriodOptions>{
            return listOf(NoDays, AllDays, CustomDays)
        }
    }
}