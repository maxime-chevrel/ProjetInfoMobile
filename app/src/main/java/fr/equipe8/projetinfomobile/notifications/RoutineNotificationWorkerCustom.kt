package fr.equipe8.projetinfomobile.notifications

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fr.equipe8.projetinfomobile.usecases.convertMillisToString
import java.util.concurrent.TimeUnit

@HiltWorker
class RoutineNotificationWorkerCustom @AssistedInject constructor (
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val notificationHelper: NotificationsHelper
) : Worker(context, params) {

    override fun doWork(): Result {
        val title = inputData.getString("ROUTINE_NAME") ?: "Routine"
        val description = inputData.getString("ROUTINE_DESC") ?: ""
        val routineId = inputData.getInt("ROUTINE_ID", -1)

        if (routineId==-1) {
            return Result.failure()
        }

        notificationHelper.showRoutineNotification(title, description, routineId)

        scheduleNextRoutine(title,description,routineId)

        return Result.success()
    }


    private fun scheduleNextRoutine(
        title: String,
        description: String,
        routineId: Int
    ) {
        val nextDelayMillis = TimeUnit.DAYS.toMillis(7)
        val data = workDataOf(
            "ROUTINE_NAME" to title,
            "ROUTINE_DESC" to description,
            "ROUTINE_ID" to routineId
        )

        Log.d( "Schedule Routine Notification id: $routineId" ,
            title+ " : " + convertMillisToString(nextDelayMillis)
        )

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)  // Ne pas suspendre si la batterie est faible
            .setRequiresCharging(false)  // Permet de fonctionner même sans charge
            .build()

        val nextWork = OneTimeWorkRequestBuilder<RoutineNotificationWorkerCustom>()
            .setInputData(data)
            .setInitialDelay(nextDelayMillis, TimeUnit.MILLISECONDS)
            .addTag("routine_${routineId}")
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(nextWork)
    }

}
