package com.capstoneprojectg8.schoolscheduleapp.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.auth.UserProfileActivity
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.ClassSettingsActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat() {

    private val auth = FirebaseAuth.getInstance()

    private val viewModel: SettingsViewModel by lazy {
        SettingsViewModel()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val classPreference: Preference? = findPreference("classes")

        classPreference?.setOnPreferenceClickListener {
            val intent = Intent(activity, ClassSettingsActivity::class.java)
            startActivity(intent)

            true
        }

        val userPreference: Preference? = findPreference("user")

        userPreference?.setOnPreferenceClickListener {
            val intent = Intent(activity, UserProfileActivity::class.java)
            startActivity(intent)

            true
        }

        listenToAuthUser()
    }

    private fun listenToAuthUser() {
        auth.addAuthStateListener {
            val userPreference: Preference? = findPreference("user")
            if (it.currentUser != null) {
                userPreference?.title = it.currentUser?.email
            }else{
                userPreference?.title = ""
            }
        }
    }

}