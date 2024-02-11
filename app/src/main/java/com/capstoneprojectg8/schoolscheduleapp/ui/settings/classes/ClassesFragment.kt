package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneprojectg8.schoolscheduleapp.MainActivity
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentClassesBinding
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs.AddClassDialogFragment

class ClassesFragment : Fragment() {

    private var _binding: FragmentClassesBinding? = null
    private val binding get() = _binding!!
    private lateinit var classesAdapter: ClassesRvAdapter
    private lateinit var classViewModel: ClassesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        classViewModel = (activity as MainActivity).classesViewModel



        setUpAdapter()
        initRecyclerView()

        activity?.let {
            classViewModel.getAllClasses().observe(viewLifecycleOwner) { classes ->
                Log.d("ClassesFragment", "Observer triggered with data: $classes")
                classesAdapter.appendClass(classes)
            }
        }

        binding.addClassBtn.setOnClickListener {
            AddClassDialogFragment().show(parentFragmentManager, "dialog")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}