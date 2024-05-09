package com.example.cs501finalproject.ui

import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.R
import com.example.cs501finalproject.util.NotificationsManager
import com.example.cs501finalproject.util.ThemeManager
import java.util.*

// -----------------------------------------------------------------------------
// Please turn on -notifications- & -Alarms&reminders- for app cs501finalproject
// -----------------------------------------------------------------------------


object NotificationsPreferencesManager {
    var notificationsEnabled: Boolean = false
    var notificationsTime: Calendar = Calendar.getInstance()
}

@Composable
fun SettingsNotificationsPage(navController: NavController) {
    val context = LocalContext.current
    val colors = ThemeManager.getAppThemeColors()

    // Remember the current state of notification settings
    var notificationsEnabled by remember { mutableStateOf(NotificationsPreferencesManager.notificationsEnabled) }
    var selectedTime by remember { mutableStateOf(NotificationsPreferencesManager.notificationsTime) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Permission Required") },
            text = { Text("This app requires notification and alarm permissions to function properly. Please enable them in settings.") },
            confirmButton = {
                Button(onClick = {
                    showPermissionDialog = false
                    NotificationsManager.checkNotificationPermissions(context)
                    NotificationsManager.checkAlarmPermissions(context)
                }) {
                    Text("Check Permissions")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.Settings_Notifications_NotificationSetting)) },
                backgroundColor = colors.primaryVariant,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        backgroundColor = colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.Settings_Notifications_EnableNotifications), style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.width(8.dp))
                Switch( //switch component to toggle notifications on or off
                    // binds the switch's checked state to notificationsEnabled
                    checked = notificationsEnabled,
                    onCheckedChange = { enabled ->
                        if (enabled && (!NotificationsManager.checkNotificationPermissions(context) || !NotificationsManager.checkAlarmPermissions(context))) {
                            // if enabling and permissions are not granted, show a permission dialog and set switch off
                            showPermissionDialog = true
                            notificationsEnabled = false
                        } else {
                            // otherwise, toggle the state and either schedule or cancel notifications
                            notificationsEnabled = enabled
                            NotificationsPreferencesManager.notificationsEnabled = enabled
                            if (enabled) NotificationsManager.scheduleNotification(context, selectedTime) else NotificationsManager.cancelNotification(context)
                        }
                    },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF55992A))
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.Settings_Notifications_NotificationTime, formatTime(selectedTime)),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )
                // button to open a time picker dialog
                Button(
                    onClick = { showTimePicker(context, selectedTime) { time ->
                        selectedTime = time
                        NotificationsPreferencesManager.notificationsTime = time
                        if (notificationsEnabled) {
                            NotificationsManager.scheduleNotification(context, time)
                        }
                    } },
                    // only enables the button if notifications are enabled
                    enabled = notificationsEnabled,
                    colors = ButtonDefaults.buttonColors(backgroundColor = colors.secondaryVariant)
                ) {
                    Text(stringResource(R.string.Settings_Notifications_ChangeTime))
                }
            }
        }
    }
}

// Function to display a time picker dialog
fun showTimePicker(context: Context, selectedTime: Calendar, onTimeSet: (Calendar) -> Unit) {
    TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val newTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            onTimeSet(newTime)
        },
        selectedTime.get(Calendar.HOUR_OF_DAY),
        selectedTime.get(Calendar.MINUTE),
        true
    ).show()
}

// Utility function to format time in HH:mm format
fun formatTime(calendar: Calendar): String {
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    Log.d("FormatTime", "Hour: $hour, Minute: $minute")
    return String.format("%02d:%02d", hour, minute)
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationsSettingsPage() {
    val navController = rememberNavController()
    SettingsNotificationsPage(navController)
}

