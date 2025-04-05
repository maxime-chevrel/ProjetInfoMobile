package fr.equipe8.projetinfomobile.usecases

import android.content.Context
import android.util.Log
import androidx.work.WorkManager
import fr.equipe8.projetinfomobile.ui.RoutineVM

class CancelRoutineNotificationUseCase(private val context: Context) {
    operator fun invoke(routine: RoutineVM){
        Log.d("CancelRoutineNotificationUseCase", "Canceling notification for routine: ${routine.name}")
        WorkManager.getInstance(context).cancelAllWorkByTag("routine_${routine.id}")
    }
}