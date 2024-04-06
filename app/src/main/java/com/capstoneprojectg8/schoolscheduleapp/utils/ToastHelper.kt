package com.capstoneprojectg8.schoolscheduleapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.capstoneprojectg8.schoolscheduleapp.R

object ToastHelper {
    fun showCustomToast(context: Context, message: String) {
        val viewRoot =
            LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null, false)

        val textView = viewRoot.findViewById<TextView>(R.id.text_message)
        textView.text = message

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = viewRoot
        toast.show()
    }
}