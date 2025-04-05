package fr.equipe8.projetinfomobile.notifications

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.equipe8.projetinfomobile.MainActivity
import javax.inject.Inject


class NotificationsHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManagerCompat,
    private val notificationBuilder: NotificationCompat.Builder,
)
{

        // Vérifier si nous avons la permission d'afficher des notifications
    private fun hasNotificationPermission(): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Pour Android 13 et supérieur, vérification explicite nécessaire
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
    }

    fun showRoutineNotification(
        title: String,
        content: String,
        routineId: Int
    ) {
        if (hasNotificationPermission()) {

            try {
                val intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("routineId", routineId)
                    Log.d( "NotificationsHelper", "Routine ID: $routineId")
                }
                val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    PendingIntent.FLAG_IMMUTABLE
                else 0

                val clickPendingIntent = PendingIntent.getActivity(
                    context, routineId, intent, flag
                )
                val notification = notificationBuilder
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(clickPendingIntent)
                    .setAutoCancel(true)
                    .build()
                notificationManager.notify(routineId, notification)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        } else {
            Log.d("NotificationsHelper", "Permission de notification non accordée")
        }
    }


}
