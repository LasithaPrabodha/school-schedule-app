package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.capstoneprojectg8.schoolscheduleapp.MainActivity
import com.capstoneprojectg8.schoolscheduleapp.databinding.AddClassDialogBinding
import com.capstoneprojectg8.schoolscheduleapp.model.Class
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.ClassesViewModel

class AddClassDialogFragment : DialogFragment() {

    private lateinit var binding: AddClassDialogBinding
    private lateinit var classesViewModel: ClassesViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddClassDialogBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.addNewClassAddDialogBtn.setOnClickListener {
            addClass()
            Log.d("DialogFragment", "Test")
        }

        return builder.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        classesViewModel = (activity as MainActivity).classesViewModel
    }


    private fun addClass() {
        val classCode = binding.classCodeAddDialogInputText.text.toString().trim()
        val className = binding.classNameAddDialogInputText.text.toString().trim()

        if (classCode.isNotEmpty() && className.isNotEmpty()) {
            val classes = Class(0, classCode, className, "red")
            classesViewModel.addClass(classes)
            Toast.makeText(context, "Class added", Toast.LENGTH_LONG).show()

            dismiss()
        }
    }
}