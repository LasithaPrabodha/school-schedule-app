package com.capstoneprojectg8.schoolscheduleapp.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.ClassSettingsActivity

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val classPreference: Preference? = findPreference("classes")

        classPreference?.setOnPreferenceClickListener {
            val intent = Intent(activity, ClassSettingsActivity::class.java)
            startActivity(intent)

            true
        }
    }
}