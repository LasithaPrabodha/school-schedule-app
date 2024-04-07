package com.capstoneprojectg8.schoolscheduleapp.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
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
        updateAssetTint()

        val switchDarkModePreference: SwitchPreferenceCompat? = findPreference("switch_dark_mode")

        switchDarkModePreference?.isChecked = isDarkModeEnabled()

        switchDarkModePreference?.setOnPreferenceChangeListener { _ , isSelected ->
            if(isSelected as Boolean){
                enableDarkMode()
            } else {
                disableDarkMode()
            }
            true
        }
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        requireActivity().recreate()
        saveDarkModePreference(true)
        updateAssetTint()
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        requireActivity().recreate()
        saveDarkModePreference(false)
        updateAssetTint()
    }

    private fun saveDarkModePreference(isDarkModeEnabled: Boolean) {
        val sharedPreferences = activity?.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) ?: return
        with(sharedPreferences.edit()) {
            putBoolean("dark_mode", isDarkModeEnabled)
            apply()
        }
    }

    private fun isDarkModeEnabled(): Boolean {
        val sharedPreferences = activity?.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences?.getBoolean("dark_mode", false) ?: false
    }

    private fun updateAssetTint() {
        val isDarkModeEnabled = isDarkModeEnabled()
        val tintResId = if (isDarkModeEnabled) R.color.background else R.color.black

        val switchDarkModePreference: SwitchPreferenceCompat? = findPreference("switch_dark_mode")
        val classesDarkModePreference: Preference? = findPreference("classes")
        val userDarkModePreference: Preference? = findPreference("user")

        switchDarkModePreference?.icon?.setTint(ContextCompat.getColor(requireContext(), tintResId))
        classesDarkModePreference?.icon?.setTint(ContextCompat.getColor(requireContext(), tintResId))
        userDarkModePreference?.icon?.setTint(ContextCompat.getColor(requireContext(), tintResId))

        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(tintResId)
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