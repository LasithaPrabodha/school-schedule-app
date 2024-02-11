package com.capstoneprojectg8.schoolscheduleapp.ui.settings

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.capstoneprojectg8.schoolscheduleapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val classPreference: Preference? = findPreference("classes")

        classPreference?.setOnPreferenceClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionNavigationSettingsToClassesFragment())
            true
        }
    }
}