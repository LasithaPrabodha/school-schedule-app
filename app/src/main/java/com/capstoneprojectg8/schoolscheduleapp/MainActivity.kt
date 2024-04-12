package com.capstoneprojectg8.schoolscheduleapp

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.capstoneprojectg8.schoolscheduleapp.databinding.ActivityMainBinding
import com.capstoneprojectg8.schoolscheduleapp.utils.ThemeHelper.isDarkModeEnabled
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val darkMode = sharedPreferences.getBoolean("dark_mode", false)

        AppCompatDelegate.setDefaultNightMode(if (darkMode) MODE_NIGHT_YES else MODE_NIGHT_NO)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_schedule,
                R.id.navigation_assignments,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> navController.navigate(R.id.navigation_home)
                R.id.navigation_schedule -> navController.navigate(R.id.navigation_schedule)
                R.id.navigation_settings -> navController.navigate(R.id.navigation_settings)
                R.id.navigation_assignments -> navController.navigate(R.id.navigation_assignments)
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}