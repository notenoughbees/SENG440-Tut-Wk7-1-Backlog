package nz.ac.uclive.dsi61.backlog

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

// STEP 1
fun Bundle.toParamString() = keySet().map { "$it -> ${get(it)}" }.joinToString("\n")
class AlarmReceiver : BroadcastReceiver() {
    // STEP 1
//    override fun onReceive(context: Context, intent: Intent) {
//        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
//        Log.d("FOO", "Received message ${intent.action} with \n${intent.extras?.toParamString()}")
//    }

    // STEP 3
    @SuppressLint("ServiceCast")
    /**
     * Sends a notification.
     */
    override fun onReceive(context: Context, intent: Intent) {
//        Log.d("FOO", "Received message ${intent.action} with \n${intent.extras?.toParamString()}")
        Log.d("FOO", "AlarmReceiver onReceive!")
        // STEP 4
        val intent: PendingIntent = Intent(context, PictureActivity::class.java).run {
            PendingIntent.getActivity(context, 0, this, 0)
        }

        // STEP 3
        val notification = Notification.Builder(context, Notification.CATEGORY_REMINDER).run {
            setSmallIcon(R.drawable.camera)
            setContentTitle("A new day, a new memory :)")
            setContentText("Just a friendly reminder to take today's picture :D")
            setContentIntent(intent)
            setAutoCancel(true) // STEP 5
            build()
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)




        // STEP 6: Time Picker
        // COMMENT OUT: this creates new notif every second
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, intent)
    }

}