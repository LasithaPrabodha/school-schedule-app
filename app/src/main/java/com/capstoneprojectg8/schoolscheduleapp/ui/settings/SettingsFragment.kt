package com.capstoneprojectg8.schoolscheduleapp.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.capstoneprojectg8.schoolscheduleapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}