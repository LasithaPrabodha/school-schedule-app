package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.addassignment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentAddNewAssignmentBinding

class AddNewAssignmentFragment : Fragment() {

    private var _binding: FragmentAddNewAssignmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddNewAssignmentViewModel by viewModels()

    private val items = listOf("Android", "iOS", "Capstone Project")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewAssignmentBinding.inflate(inflater, container, false)

        val adapter = ArrayAdapter(requireContext(), R.layout.list_class_item, items)
        val autocomplete = binding.autoCompleteClass
        autocomplete.setAdapter(adapter)
        autocomplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}