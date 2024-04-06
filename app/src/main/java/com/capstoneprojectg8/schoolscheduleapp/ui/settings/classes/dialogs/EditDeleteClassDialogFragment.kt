package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentEditDeleteClassDialogBinding
import com.capstoneprojectg8.schoolscheduleapp.models.SClass
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.ClassSettingsViewModel

class EditDeleteClassDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentEditDeleteClassDialogBinding
    private val classSettingsViewModel: ClassSettingsViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentEditDeleteClassDialogBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val classId = requireArguments().getInt(ARG_CLASS_ID, 0)
        val classCode = requireArguments().getString(ARG_CLASS_CODE, "")
        val className = requireArguments().getString(ARG_CLASS_NAME, "")
        val classColour = requireArguments().getInt(ARG_CLASS_COLOUR, 0)

        binding.classCodeEditDialogInputText.setText(classCode)
        binding.classNameEditDialogInputText.setText(className)

        binding.saveEditClassDialogBtn.setOnClickListener {
            editClass(classId, classColour)
        }

        binding.deleteImg.setOnClickListener {
            deleteClass(classId, classCode, className, classColour)
        }

        binding.addNewClassCancelDialogBtn.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }


    private fun editClass(classId: Int, classColour: Int) {
        val editedCode = binding.classCodeEditDialogInputText.text.toString().trim()
        val editedName = binding.classNameEditDialogInputText.text.toString().trim()

        if(editedCode.isNotEmpty() && editedName.isNotEmpty()){
            val editedSClass = SClass(classId, editedCode, editedName, classColour)
            classSettingsViewModel.editClass(editedSClass)
            Toast.makeText(context, "Class edited", Toast.LENGTH_LONG).show()
            dismiss()
        } else if (editedCode.isNotEmpty()) {
            binding.textInputClassName.error = "Enter class name"
            binding.textInputClassCode.error = null
        } else if (editedName.isNotEmpty()) {
            binding.textInputClassCode.error = "Enter class code"
            binding.textInputClassName.error = null
        } else if (editedCode.isEmpty() && editedName.isEmpty()) {
            binding.textInputClassCode.error = "Enter class code"
            binding.textInputClassName.error = "Enter class name"
        }

    }

    private fun deleteClass(classId: Int, classCode: String, className:String, classColour:Int){
        val currentSClass = SClass(classId, classCode, className, classColour)
        AlertDialog.Builder(activity).apply {
            setTitle("Delete class")
            setMessage("Are you sure you want to delete this class")
            setPositiveButton("Delete"){_,_ ->
                classSettingsViewModel.deleteClass(currentSClass)
                Toast.makeText(context, "Class deleted", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    companion object {
        private const val ARG_CLASS_ID = "arg_class_id"
        private const val ARG_CLASS_CODE = "arg_class_code"
        private const val ARG_CLASS_NAME = "arg_class_name"
        private const val ARG_CLASS_COLOUR = "arg_class_colour"

        fun newInstance(classId: Int, classCode: String, className: String, classColour:Int): EditDeleteClassDialogFragment {
            val args = Bundle().apply {
                putInt(ARG_CLASS_ID, classId)
                putString(ARG_CLASS_CODE, classCode)
                putString(ARG_CLASS_NAME, className)
                putInt(ARG_CLASS_COLOUR, classColour)
            }
            return EditDeleteClassDialogFragment().apply {
                arguments = args
            }
        }
    }


}