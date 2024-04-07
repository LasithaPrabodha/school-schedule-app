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
            val newSClass = SClass(0, classCode, className, getRandomColorName())
            classSettingsViewModel.addClass(newSClass)
            Toast.makeText(context, "Class added", Toast.LENGTH_SHORT).show()
            dismiss()
        } else if(classCode.isNotEmpty()) {
            binding.textInputClassName.error = "Enter class name"
            binding.textInputClassCode.error = null
        } else if(className.isNotEmpty()){
            binding.textInputClassCode.error = "Enter class code"
            binding.textInputClassName.error = null
        } else if (className.isEmpty() && classCode.isEmpty()) {
            binding.textInputClassCode.error = "Enter class code"
            binding.textInputClassName.error = "Enter class name"
        }
    }

    private fun getRandomColorName(): Int {
        val colorNames = arrayListOf(
            R.color.purple_200,
            R.color.purple_500,
            R.color.teal_200,
            R.color.teal_700,
            R.color.aqua,
            R.color.Aquamarine,
            R.color.Blue,
            R.color.BlueViolet,
            R.color.Brown,
            R.color.BurlyWood,
            R.color.CadetBlue,
            R.color.Chartreuse,
            R.color.Chocolate,
            R.color.Coral,
            R.color.CornflowerBlue,
            R.color.Crimson,
            R.color.Cyan,
            R.color.DarkCyan,
            R.color.DarkGoldenrod,
            R.color.DarkGreen,
            R.color.DarkKhaki,
            R.color.DarkMagenta,
            R.color.DarkOliveGreen,
            R.color.DarkOrange,
            R.color.DarkOrchid,
            R.color.DarkRed,
            R.color.DarkSalmon,
            R.color.DarkSeaGreen,
            R.color.DarkSlateBlue,
            R.color.DarkSlateGray,
            R.color.DarkTurquoise,
            R.color.DarkViolet,
            R.color.DeepPink,
            R.color.DeepSkyBlue,
            R.color.DimGray,
            R.color.DodgerBlue,
            R.color.FireBrick,
            R.color.ForestGreen,
            R.color.Fuchsia,
            R.color.gray,
            R.color.Green,
            R.color.GreenYellow,
            R.color.HotPink,
            R.color.IndianRed,
            R.color.LawnGreen,
            R.color.LightCoral,
            R.color.LightGoldenrodYellow,
            R.color.LightGreen,
            R.color.LightPink,
            R.color.LightSalmon,
            R.color.LightSeaGreen,
            R.color.LightSkyBlue,
            R.color.LightSlateGray,
            R.color.LightSteelBlue,
            R.color.lime,
            R.color.LimeGreen,
            R.color.Magenta,
            R.color.MediumAquamarine,
            R.color.MediumOrchid,
            R.color.MediumPurple,
            R.color.MediumSeaGreen,
            R.color.MediumSlateBlue,
            R.color.MediumSpringGreen,
            R.color.MediumTurquoise,
            R.color.MediumVioletRed,
            R.color.olive,
            R.color.Olive,
            R.color.OliveDrab,
            R.color.Orange,
            R.color.OrangeRed,
            R.color.Orchid,
            R.color.PaleGoldenrod,
            R.color.PaleGreen,
            R.color.PaleVioletRed,
            R.color.Peru,
            R.color.Pink,
            R.color.Plum,
            R.color.purple,
            R.color.red,
            R.color.RosyBrown,
            R.color.RoyalBlue,
            R.color.SaddleBrown,
            R.color.Salmon,
            R.color.SandyBrown,
            R.color.SeaGreen,
            R.color.Sienna,
            R.color.SlateBlue,
            R.color.SpringGreen,
            R.color.SteelBlue,
            R.color.Tan,
            R.color.teal,
            R.color.Teal,
            R.color.Tomato,
            R.color.Violet,
            R.color.YellowGreen,
            R.color.orange,
            R.color.green,
            R.color.blue,
        )


        val randomArrayIndex = Random.nextInt(colorNames.size)
        return colorNames[randomArrayIndex]
    }
}