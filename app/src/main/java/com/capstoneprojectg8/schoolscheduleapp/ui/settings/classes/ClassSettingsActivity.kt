package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.ActivityClassSettingsBinding
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs.AddClassDialogFragment
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs.EditDeleteClassDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassSettingsActivity : AppCompatActivity() {
    private var _binding: ActivityClassSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var classesAdapter: ClassesRvAdapter
    private val viewModel: ClassSettingsViewModel by viewModels<ClassSettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityClassSettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.window.statusBarColor = resources.getColor(R.color.transparent)


        setUpAdapter()
        initRecyclerView()

        viewModel.getAllClasses().observe(this) { classes ->
            Log.d("ClassesFragment", "Observer triggered with data: $classes")
            classesAdapter.appendClass(classes)
        }

        binding.addClassBtn.setOnClickListener {
            AddClassDialogFragment().show(supportFragmentManager, "dialog")
        }

        classesAdapter.onItemClick = { selectedClass ->
            val editDeleteClassDialogFragment = EditDeleteClassDialogFragment.newInstance(
                selectedClass.id,
                selectedClass.code,
                selectedClass.name,
                selectedClass.colour
            )
            editDeleteClassDialogFragment.show(supportFragmentManager, "dialog2")

        }


    }

    private fun setUpAdapter() {
        classesAdapter = ClassesRvAdapter()
    }

    private fun initRecyclerView() {
        binding.classesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = classesAdapter
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}