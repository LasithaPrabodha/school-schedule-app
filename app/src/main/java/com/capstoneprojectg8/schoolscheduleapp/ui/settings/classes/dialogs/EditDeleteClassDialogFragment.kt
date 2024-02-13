package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.capstoneprojectg8.schoolscheduleapp.MainActivity
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentEditDeleteClassDialogBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.ClassesViewModel

class EditDeleteClassDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentEditDeleteClassDialogBinding
    private lateinit var classesViewModel: ClassesViewModel

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
            dismiss()
        }

        binding.deleteImg.setOnClickListener {
            deleteClass(classId, classCode, className, classColour)
        }

        return builder.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        classesViewModel = (activity as MainActivity).classesViewModel

    }

    private fun editClass(classId: Int, classColour: Int) {
        val editedCode = binding.classCodeEditDialogInputText.text.toString().trim()
        val editedName = binding.classNameEditDialogInputText.text.toString().trim()

        if(editedCode.isNotEmpty() && editedName.isNotEmpty()){
            val editedClass = Class(classId, editedCode, editedName, classColour)
            classesViewModel.editClass(editedClass)
            Toast.makeText(context, "Class edited", Toast.LENGTH_LONG).show()
            dismiss()
        } else {
            Toast.makeText(context, "Insert class code or class name", Toast.LENGTH_LONG).show()
        }

    }

    private fun deleteClass(classId: Int, classCode: String, className:String, classColour:Int){
        val currentClass = Class(classId, classCode, className, classColour)
        AlertDialog.Builder(activity).apply {
            setTitle("Delete class")
            setMessage("Are you sure you want to delete this class")
            setPositiveButton("Delete"){_,_ ->
                classesViewModel.deleteClass(currentClass)
                Toast.makeText(context, "Class deleted", Toast.LENGTH_LONG).show()
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