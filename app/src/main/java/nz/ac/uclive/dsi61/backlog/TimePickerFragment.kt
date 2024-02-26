package nz.ac.uclive.dsi61.backlog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

// STEP 6
class TimePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(activity, listener, 8, 0, false)
    }

    var listener: TimePickerDialog.OnTimeSetListener? = null

}