package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.add_assignment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentAddNewAssignmentBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.ClassSlot
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.ClassSettingsViewModel
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class AddNewAssignmentFragment : Fragment() {

    private var _binding: FragmentAddNewAssignmentBinding? = null
    private val binding get() = _binding!!
    private val assignmentViewModel: AddNewAssignmentViewModel by activityViewModels()
    private val classSettingsViewModel: ClassSettingsViewModel by activityViewModels()
    private var selectedClassSlot: ClassSlot? = null
    private var classSlotId: Int? = null
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentAddNewAssignmentBinding.inflate(inflater, container, false)
        calendar = Calendar.getInstance()
        val today = LocalDate.now()
        val localDate = DateHelper.startOfTheWeek(today)
        val finalDate = if (localDate > today) localDate else today
        calendar.set(finalDate.year, finalDate.monthValue - 1, finalDate.dayOfMonth, 9, 0)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.editTextDate.setText(dateFormat.format(calendar.time))

        binding.editTextDate.setOnClickListener { showDatePicker() }

        classSlotId = arguments?.getInt("classSlotId", 0)


        val autocomplete = binding.autoCompleteClass

        classSettingsViewModel.getAllClassSlots().observe(viewLifecycleOwner) { classes ->
            if (classSlotId != null && classSlotId != 0) {
                assignmentViewModel.getClassSlotById(classSlotId!!).observe(viewLifecycleOwner) {
                    autocomplete.setText(it.className, false)
                    selectedClassSlot = it
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
                    selectedClassSlot = classes.find { it.className == className }!!
                }
        }

        binding.addNewAssignmentBtn.setOnClickListener {
            lifecycleScope.launch {
                addAssignment()
            }
        }

        binding.cancelAddAssignmentBtn.setOnClickListener {
            closeFragment()
        }

        return binding.root
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, _year, _month, dayOfMonth ->
                calendar.set(_year, _month, dayOfMonth)
                updateDateEditText()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun updateDateEditText() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.editTextDate.setText(dateFormat.format(calendar.time))
    }

    private suspend fun addAssignment() {
        val assignmentTitle = binding.assignmentTitleInputText.text.toString().trim()
        val assignmentDetail = binding.detailsTextInput.text.toString().trim()
        val priority = binding.setPriorityCheckBox.isChecked
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dueDate = dateFormat.format(calendar.time)

        if (assignmentTitle.isEmpty()) {
            binding.assignmentTitleInputLayout.error = "Please insert assignment title"
        }

        if (selectedClassSlot == null) {
            binding.classListInputLayout.error = "Please select class"
        }

        if (assignmentTitle.isEmpty() || selectedClassSlot == null) return

        val newAssignment =
            Assignment(
                0,
                assignmentTitle,
                assignmentDetail,
                dueDate,
                priority,
                false,
                selectedClassSlot!!.classId,
                classSlotId!!
            )
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