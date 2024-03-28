package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.addassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentAddNewAssignmentBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.ClassSlot
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.ClassSettingsViewModel

class AddNewAssignmentFragment : Fragment() {

    private var _binding: FragmentAddNewAssignmentBinding? = null
    private val binding get() = _binding!!
    private val assignmentViewModel: AddNewAssignmentViewModel by activityViewModels()
    private val classSettingsViewModel: ClassSettingsViewModel by activityViewModels()
    private var selectedClass: ClassSlot? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentAddNewAssignmentBinding.inflate(inflater, container, false)

        val classId = arguments?.getInt("classId", 0)


        val autocomplete = binding.autoCompleteClass

        classSettingsViewModel.getAllClassSlots().observe(viewLifecycleOwner) { classes ->
            if (classId != null && classId != 0) {
                assignmentViewModel.getDefaultListValue(classId).observe(viewLifecycleOwner) {
                    autocomplete.setText(it.className, false)
                    selectedClass = it
                }
            } else {
                binding.autoCompleteClass.isEnabled = true
            }

            val classNameAndCode = classes.map { it.className }
            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, classNameAndCode)
            autocomplete.setAdapter(adapter)

            autocomplete.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                    val itemSelected = adapterView.getItemAtPosition(i) as String
                    val className = itemSelected.split(" - ")[0]
                    selectedClass = classes.find { it.className == className }!!
                }
        }

        binding.addNewAssignmentBtn.setOnClickListener {
            addAssignment()
        }

        binding.cancelAddAssignmentBtn.setOnClickListener {
            closeFragment()
        }

        return binding.root
    }

    private fun addAssignment() {
        val assignmentTitle = binding.assignmentTitleInputText.text.toString().trim()
        val assignmentDetail = binding.detailsTextInput.text.toString().trim()

        if (assignmentTitle.isEmpty()) {
            binding.assignmentTitleInputLayout.error = "Please insert assignment title"
        }

        if (selectedClass == null) {
            binding.classListInputLayout.error = "Please select class"
        }

        if(assignmentTitle.isEmpty() || selectedClass == null) return

        val newAssignment =
            Assignment(0, assignmentTitle, assignmentDetail, false, false, selectedClass!!.id)
        assignmentViewModel.addAssignment(newAssignment)
        Toast.makeText(context, "Assignment added", Toast.LENGTH_SHORT).show()
        closeFragment()

    }

    private fun closeFragment() {
        findNavController().navigateUp()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}