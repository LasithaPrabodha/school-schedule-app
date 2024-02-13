package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.database.ClassesDatabase
import com.capstoneprojectg8.schoolscheduleapp.databinding.ActivityClassSettingsBinding
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs.AddClassDialogFragment
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs.EditDeleteClassDialogFragment

class ClassSettingsActivity : AppCompatActivity() {
    private var _binding: ActivityClassSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var classesAdapter: ClassesRvAdapter
    private lateinit var viewModel: ClassesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityClassSettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_new_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        setUpViewModel()
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
                selectedClass.classCode,
                selectedClass.className,
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

    private fun setUpViewModel(){
        val classesRepository = ClassesRepository(ClassesDatabase(this))
        val viewModelProviderFactory = ClassesViewModelFactory(application, classesRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[ClassesViewModel::class.java]
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}