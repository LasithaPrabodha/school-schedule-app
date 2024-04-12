package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.AddClassDialogBinding
import com.capstoneprojectg8.schoolscheduleapp.models.SClass
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.ClassSettingsViewModel
import com.capstoneprojectg8.schoolscheduleapp.utils.ThemeHelper
import kotlin.random.Random

class AddClassDialogFragment : DialogFragment() {

    private lateinit var binding: AddClassDialogBinding
    private val classSettingsViewModel: ClassSettingsViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddClassDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)


        binding.addNewClassAddDialogBtn.setOnClickListener {
            addClass()
        }

        binding.addNewClassCancelDialogBtn.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }


    private fun addClass() {
        val classCode = binding.classCodeAddDialogInputText.text.toString().trim()
        val className = binding.classNameAddDialogInputText.text.toString().trim()


        if (classCode.isNotEmpty() && className.isNotEmpty()) {
            val newSClass = SClass(0, classCode, className, ThemeHelper.getRandomColor())
            classSettingsViewModel.addClass(newSClass)
            Toast.makeText(context, "Class added", Toast.LENGTH_SHORT).show()
            dismiss()
        } else if (classCode.isNotEmpty()) {
            binding.textInputClassName.error = "Enter class name"
            binding.textInputClassCode.error = null
        } else if (className.isNotEmpty()) {
            binding.textInputClassCode.error = "Enter class code"
            binding.textInputClassName.error = null
        } else if (className.isEmpty() && classCode.isEmpty()) {
            binding.textInputClassCode.error = "Enter class code"
            binding.textInputClassName.error = "Enter class name"
        }
    }

}