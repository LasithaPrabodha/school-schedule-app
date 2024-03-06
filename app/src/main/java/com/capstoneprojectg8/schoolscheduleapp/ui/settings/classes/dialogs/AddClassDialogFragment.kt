package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.capstoneprojectg8.schoolscheduleapp.MainActivity
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.AddClassDialogBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.ClassesViewModel
import kotlin.random.Random

class AddClassDialogFragment : DialogFragment() {

    private lateinit var binding: AddClassDialogBinding
    private val classesViewModel: ClassesViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddClassDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.addNewClassAddDialogBtn.setOnClickListener {
            addClass()
        }

        return builder.create()
    }



    private fun addClass() {
        val classCode = binding.classCodeAddDialogInputText.text.toString().trim()
        val className = binding.classNameAddDialogInputText.text.toString().trim()

        if (classCode.isNotEmpty() && className.isNotEmpty()) {
            val classes = Class(0, classCode, className, getRandomColorName())
            classesViewModel.addClass(classes)
            Toast.makeText(context, "Class added", Toast.LENGTH_LONG).show()
            dismiss()
        }
    }

    private fun getRandomColorName(): Int {
        val colorNames = arrayListOf(
            R.color.purple_200,
            R.color.purple_500,
            R.color.purple_700,
            R.color.teal_200,
            R.color.teal_700,
            R.color.purple,
            R.color.orange,
            R.color.green,
            R.color.blue,
        )


        val randomArrayIndex = Random.nextInt(colorNames.size)
        return colorNames[randomArrayIndex]
    }
}