package nz.ac.uclive.dsi61.backlog

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import java.util.Calendar

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    // STEP 1
    private val AlarmReceiver = AlarmReceiver()

    override fun onStart() {
        super.onStart()
        Log.d("FOO", "MainActivity started!")
        registerReceiver(AlarmReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onStop() {
        super.onStop()
        Log.d("FOO", "MainActivity stopped!")
        unregisterReceiver(AlarmReceiver)
    }

    // STEP 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FOO", "MainActivity created!")
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        setReminderTime() // STEP 7
    }


    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(Notification.CATEGORY_REMINDER, "Daily Reminders", importance).apply {
            description = "Send daily reminders to capture memories"
        }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // STEP 7
    private fun setReminderTime() {
        val fragment = TimePickerFragment()
        fragment.listener = this // MainActivity must now implement TimePickerDialog.OnTimeSetListener
        fragment.show(supportFragmentManager, null)
    }

    // STEP 8
    override fun onTimeSet(picker: TimePicker, hour: Int, minute: Int) {
        val today = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val intent = Intent(applicationContext, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(applicationContext, 0, it, 0)
        }

        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(AlarmManager.RTC, today.timeInMillis, AlarmManager.INTERVAL_DAY, intent)

        // STEP 11
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.edit().apply {
            putInt("hour", hour)
            putInt("minute", minute)
            apply()
        }
        Utilities.scheduleReminder(applicationContext, hour, minute)

        // STEP 13: set the boot receiver to enabled
        val receiver = ComponentName(this, BootReceiver::class.java)
        packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
    }


}

