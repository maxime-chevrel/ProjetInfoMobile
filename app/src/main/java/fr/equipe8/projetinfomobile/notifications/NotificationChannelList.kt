package fr.equipe8.projetinfomobile.notifications

sealed class NotificationChannelList(val id : String, val name: String) {
    data object RoutineNotificationChannel : NotificationChannelList(
        id = "Routine Notifications Channel ID",
        name = "Routine Notifications Channel"
    )
}
