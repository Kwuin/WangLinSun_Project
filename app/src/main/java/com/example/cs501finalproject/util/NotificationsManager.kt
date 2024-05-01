package com.example.cs501finalproject.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.cs501finalproject.R
import java.util.Calendar

// -----------------------------------------------------------------------------
// Please turn on -notifications- & -Alarms&reminders- for app cs501finalproject
// -----------------------------------------------------------------------------

object NotificationsManager {
    // Unique ID for the notification
    private const val NOTIFICATION_ID = 888
    // Unique ID for the notification channel
    private const val CHANNEL_ID = "blog_notification_channel"

    // Method to schedule a notification
    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(context: Context, time: Calendar) {
        // Ensure any existing alarm is cancelled
        cancelNotification(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("EXTRA_NOTIFICATION_ID", 1)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
        // Set the alarm to trigger at the calendar time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time.timeInMillis, pendingIntent)
        Log.d("NotificationsManager", "Alarm set for: ${formatTime(time)}")
    }

    // Method to cancel any scheduled notification
    fun cancelNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
        alarmManager.cancel(pendingIntent)
        Log.d("NotificationsManager", "Existing alarm cancelled")
    }

    // BroadcastReceiver to handle the alarm event
    class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val currentTime = Calendar.getInstance()
            Log.d("NotificationReceiver", "Alarm received at: ${formatTime(currentTime)}")
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                createNotificationChannel(context)
                createNotification(context)
            }
        }

        // Helper method to create a notification channel for Android O and above
        private fun createNotificationChannel(context: Context) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val name = "Blog Notification"
                val descriptionText = "Notifications for blog updates"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

        // Helper method to create and display a notification
        private fun createNotification(context: Context) {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_calendar)
                .setContentTitle("Daily Reminder")
                .setContentText("It's time to write your diary!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            val notificationManager = NotificationManagerCompat.from(context)
            // Check whether acquire POST_NOTIFICATIONS permission
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Permission has not been granted, log this situation.
                Log.w("NotificationsManager", "Permission to post notifications not granted")
                return
            }
            // Permission has been granted, display the notification
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }

    // Helper method to format time as a string
    private fun formatTime(calendar: Calendar): String {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return String.format("%02d:%02d", hour, minute)
    }
}
