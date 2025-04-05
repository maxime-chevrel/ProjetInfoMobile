package fr.equipe8.projetinfomobile.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.equipe8.projetinfomobile.notifications.NotificationChannelList
import fr.equipe8.projetinfomobile.notifications.NotificationsHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val channel = NotificationChannel(
                NotificationChannelList.RoutineNotificationChannel.id,
                NotificationChannelList.RoutineNotificationChannel.name,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        return notificationManager
    }

    @Singleton
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ) : NotificationCompat.Builder {

        return NotificationCompat.Builder(context, NotificationChannelList.RoutineNotificationChannel.id)
            .setContentTitle("Routine notification")
            .setContentText("Routine notification content")
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideNotificationHelper(
        @ApplicationContext context: Context,
        notificationManager: NotificationManagerCompat,
        notificationBuilder: NotificationCompat.Builder
    ): NotificationsHelper {
        return NotificationsHelper(context, notificationManager, notificationBuilder)
    }

}