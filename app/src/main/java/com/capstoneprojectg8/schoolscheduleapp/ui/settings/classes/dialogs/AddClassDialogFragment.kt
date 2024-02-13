package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.capstoneprojectg8.schoolscheduleapp.MainActivity
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.AddClassDialogBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes.ClassesViewModel
import kotlin.random.Random

class AddClassDialogFragment : DialogFragment() {

    private lateinit var binding: AddClassDialogBinding
    private lateinit var classesViewModel: ClassesViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddClassDialogBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.addNewClassAddDialogBtn.setOnClickListener {
            addClass()
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
            R.color.black,
            R.color.white,
            R.color.AliceBlue,
            R.color.AntiqueWhite,
            R.color.aqua,
            R.color.Aquamarine,
            R.color.Azure,
            R.color.Beige,
            R.color.Bisque,
            R.color.Black,
            R.color.BlanchedAlmond,
            R.color.Blue,
            R.color.BlueViolet,
            R.color.Brown,
            R.color.BurlyWood,
            R.color.CadetBlue,
            R.color.Chartreuse,
            R.color.Chocolate,
            R.color.Coral,
            R.color.CornflowerBlue,
            R.color.Cornsilk,
            R.color.Crimson,
            R.color.Cyan,
            R.color.DarkBlue,
            R.color.DarkCyan,
            R.color.DarkGoldenrod,
            R.color.DarkGray,
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
            R.color.dullwhite,
            R.color.FireBrick,
            R.color.FloralWhite,
            R.color.ForestGreen,
            R.color.Fuchsia,
            R.color.Gainsboro,
            R.color.GhostWhite,
            R.color.Gold,
            R.color.Goldenrod,
            R.color.gray,
            R.color.Green,
            R.color.GreenYellow,
            R.color.Honeydew,
            R.color.HotPink,
            R.color.IndianRed,
            R.color.Indigo,
            R.color.Ivory,
            R.color.Khaki,
            R.color.Lavender,
            R.color.LavenderBlush,
            R.color.LawnGreen,
            R.color.LemonChiffon,
            R.color.LightBlue,
            R.color.LightCoral,
            R.color.LightCyan,
            R.color.LightGoldenrodYellow,
            R.color.LightGreen,
            R.color.LightGrey,
            R.color.LightPink,
            R.color.LightSalmon,
            R.color.LightSeaGreen,
            R.color.LightSkyBlue,
            R.color.LightSlateGray,
            R.color.LightSteelBlue,
            R.color.LightYellow,
            R.color.lime,
            R.color.LimeGreen,
            R.color.Linen,
            R.color.Magenta,
            R.color.maroon,
            R.color.MediumAquamarine,
            R.color.MediumBlue,
            R.color.MediumOrchid,
            R.color.MediumPurple,
            R.color.MediumSeaGreen,
            R.color.MediumSlateBlue,
            R.color.MediumSpringGreen,
            R.color.MediumTurquoise,
            R.color.MediumVioletRed,
            R.color.MidnightBlue,
            R.color.MintCream,
            R.color.MistyRose,
            R.color.Moccasin,
            R.color.NavajoWhite,
            R.color.navy,
            R.color.OldGold,
            R.color.OldLace,
            R.color.olive,
            R.color.Olive,
            R.color.OliveDrab,
            R.color.Orange,
            R.color.OrangeRed,
            R.color.Orchid,
            R.color.PaleGoldenrod,
            R.color.PaleGreen,
            R.color.PaleTurquoise,
            R.color.PaleVioletRed,
            R.color.PapayaWhip,
            R.color.PeachPuff,
            R.color.Peru,
            R.color.Pink,
            R.color.Plum,
            R.color.PowderBlue,
            R.color.purple,
            R.color.Purple,
            R.color.red,
            R.color.RosyBrown,
            R.color.RoyalBlue,
            R.color.SaddleBrown,
            R.color.Salmon,
            R.color.SandyBrown,
            R.color.SeaGreen,
            R.color.Seashell,
            R.color.Sienna,
            R.color.silver,
            R.color.SkyBlue,
            R.color.SlateBlue,
            R.color.SlateGray,
            R.color.Snow,
            R.color.SpringGreen,
            R.color.SteelBlue,
            R.color.Tan,
            R.color.teal,
            R.color.Teal,
            R.color.Thistle,
            R.color.Tomato,
            R.color.Turquoise,
            R.color.Violet,
            R.color.Wheat,
            R.color.White,
            R.color.WhiteSmoke,
            R.color.yellow,
            R.color.Yellow,
            R.color.YellowGreen
        )


        val randomArrayIndex = Random.nextInt(colorNames.size)
        return colorNames[randomArrayIndex]
    }
}