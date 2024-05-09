package com.example.cs501finalproject.util

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.cs501finalproject.R
import java.util.Calendar


// -----------------------------------------------------------------------------
// Please turn on -notifications- & -Alarms&reminders- for app cs501finalproject
// -----------------------------------------------------------------------------


// manages all notification-related functionalities
object NotificationsManager {
    // Unique identifier for notifications from this app
    private const val NOTIFICATION_ID = 888
    // Identifier for the notification channel
    private const val CHANNEL_ID = "custom_daily_blog_alarm"

    // schedule a notification to be triggered at a specific time
    fun scheduleNotification(context: Context, time: Calendar) {
        // Check if necessary permissions are granted before proceeding
        if (!checkNotificationPermissions(context) || !checkAlarmPermissions(context)) {
            return  // exit if any permissions are not granted
        }

        // get AlarmManager service from system
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // intent that specifies the action to perform when the alarm triggers
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("EXTRA_NOTIFICATION_ID", 1)
        }
        // pendingIntent that triggers when the alarm goes off
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0
        )
        // set an exact time for the alarm to go off
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time.timeInMillis, pendingIntent)
    }

    // cancel all scheduled notifications
    fun cancelNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0
        )
        // cancel any alarms set with this PendingIntent
        alarmManager.cancel(pendingIntent)
    }

    // checks if notification permissions are enabled and prompts the user if they are not
    fun checkNotificationPermissions(context: Context): Boolean {
        val notificationEnabled = NotificationManagerCompat.from(context).areNotificationsEnabled()
        if (!notificationEnabled) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            return false
        }
        return true
    }

    // check if the app has permission to schedule exact alarms and prompts the user if it does not
    fun checkAlarmPermissions(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !context.getSystemService(AlarmManager::class.java).canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            return false
        }
        return true
    }

    // receives broadcasts when alarms go off and handles notification posting
    class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                createNotificationChannel(context)
                createNotification(context)
            }
        }

        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val channel = NotificationChannel(CHANNEL_ID, "Blog Notification", NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = "Notifications for blog updates"
                }
                context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
            }
        }

        // prepare and displays a notification
        private fun createNotification(context: Context) {
            // Check if POST_NOTIFICATIONS permission is granted (required for Android T and above)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // Log or handle the absence of permission.
                    Log.w("NotificationsManager", "Permission to post notifications not granted")
                    return
                }
            }
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_calendar)
                .setContentTitle("Daily Reminder")
                .setContentText("It's time to write your diary!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Now it's safe to show the notification
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        }
    }
}