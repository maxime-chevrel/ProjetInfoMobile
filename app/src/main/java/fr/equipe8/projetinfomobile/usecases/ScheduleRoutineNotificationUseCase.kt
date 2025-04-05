package fr.equipe8.projetinfomobile.usecases


import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import fr.equipe8.projetinfomobile.notifications.RoutineNotificationWorkerCustom
import fr.equipe8.projetinfomobile.notifications.RoutineNotificationWorkerDaily
import fr.equipe8.projetinfomobile.ui.RoutineVM
import fr.equipe8.projetinfomobile.ui.addeditscreen.PeriodOptions
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import java.util.concurrent.TimeUnit


class ScheduleRoutineNotificationUseCase(private val context: Context) {
    operator fun invoke(routine: RoutineVM){
        if (!routine.isActive) return
       WorkManager.getInstance(context).cancelAllWorkByTag("routine_${routine.id}")
        if (routine.periodicity == PeriodOptions.CustomDays) {
            for (day in routine.daysOfWeek) {
                val data = workDataOf(
                    "ROUTINE_NAME" to routine.name,
                    "ROUTINE_DESC" to routine.description,
                    "ROUTINE_ID" to routine.id,
                )
                val now = LocalDateTime.now()


                var next = now.withHour(routine.hour.toInt())
                    .withMinute(routine.minute.toInt())
                    .withSecond(0)
                    .withNano(0)

                if (now.dayOfWeek == day) {
                    if (now.isAfter(next)) {
                        next = next.plusWeeks(1)
                    }
                } else {
                    // Sinon va au prochain bon jour
                    next = now.with(TemporalAdjusters.next(day))
                        .withHour(routine.hour.toInt())
                        .withMinute(routine.minute.toInt())
                        .withSecond(0)
                        .withNano(0)
                }

                val delayMillis = Duration.between(now, next).toMillis()
                Log.d( "Schedule Routine Notification id: ${routine.id}" ,convertMillisToString(delayMillis))
                val constraints = Constraints.Builder()
                    .setRequiresBatteryNotLow(true)  // Ne pas suspendre si la batterie est faible
                    .setRequiresCharging(false)  // Permet de fonctionner même sans charge
                    .build()

                val nextWork = OneTimeWorkRequestBuilder<RoutineNotificationWorkerCustom>()
                    .setInputData(data)
                    .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                    .addTag("routine_${routine.id}")
                    .setConstraints(constraints)
                    .build()

                WorkManager.getInstance(context).enqueue(nextWork)
            }
        }
        else if (routine.periodicity == PeriodOptions.AllDays) {
            val data = workDataOf(
                "ROUTINE_NAME" to routine.name,
                "ROUTINE_DESC" to routine.description,
                "ROUTINE_ID" to routine.id,
            )
            val now = LocalDateTime.now()
            var next = now.withHour(routine.hour.toInt()).withMinute(routine.minute.toInt())
                .withSecond(0).withNano(0)

            if (now.isAfter(next)) {
                next = next.plusDays(1)
            }
            val delayMillis = Duration.between(now, next).toMillis()

            Log.d( "Schedule Routine Notification id: ${routine.id}" ,convertMillisToString(delayMillis))

            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)  // Ne pas suspendre si la batterie est faible
                .setRequiresCharging(false)  // Permet de fonctionner même sans charge
                .build()

            val nextWork = OneTimeWorkRequestBuilder<RoutineNotificationWorkerDaily>()
                .setInputData(data)
                .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                .addTag("routine_${routine.id}")
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueue(nextWork)
        }
        else{
            return
        }
    }
}
fun convertMillisToString(millis: Long): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60)) % 60
    val hours = (millis / (1000 * 60 * 60)) % 24
    val days = millis / (1000 * 60 * 60 * 24)

    return String.format("%d jours, %02d heures, %02d minutes, %02d secondes", days, hours, minutes, seconds)
}