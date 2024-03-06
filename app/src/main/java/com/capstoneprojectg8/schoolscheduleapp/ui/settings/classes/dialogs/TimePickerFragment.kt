package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class TimePickerFragment(val listener: (String) -> (Unit)): DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar: Calendar = Calendar.getInstance()
        val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minute: Int = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(activity as Context, this, hour, minute, false)

    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        listener("$hour:$minute")



    }
}