package nz.ac.uclive.dsi61.backlog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager

// STEP 10
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // STEP 12
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        if (prefs.getInt("hour", -1) >= 0) {
            Utilities.scheduleReminder(context, prefs.getInt("hour", 8), prefs.getInt("minute", 0))
        }
    }

}