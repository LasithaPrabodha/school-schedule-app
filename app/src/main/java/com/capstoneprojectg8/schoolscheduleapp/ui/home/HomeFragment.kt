package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentHomeBinding
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var weekDaysLayout: LinearLayout
    private var cellWidth: Int = 0
    private val today = DateHelper.getToday("dd")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: ClassesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        setHasOptionsMenu(true);

        val displayMetrics = resources.displayMetrics
        var dpWidth = displayMetrics.widthPixels

        weekDaysLayout = binding.dayScroll

        dpWidth -= weekDaysLayout.marginStart

        cellWidth = dpWidth / 5

        val dayOfWeek = DateHelper.generateDaysOfTheWeek()

        for (i in 0 until dayOfWeek.size) {
            generateWeekDay(dayOfWeek[i])
        }

        return root
    }

    private fun setupRecyclerView(viewModel: HomeViewModel) {
        adapter = ClassesAdapter(requireContext(), emptyList())

        binding.rvAssignments.apply{
            layoutManager = LinearLayoutManager(context)
            adapter= this@HomeFragment.adapter
        }

        viewModel.classes.observe(viewLifecycleOwner) {classList ->
            adapter.updateData(classList)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.loadClassItems()
        setupRecyclerView(homeViewModel)
    }

    private fun generateWeekDay(weekday: Map<String, String>) {
        val linearLayout = LinearLayout(activity)
        linearLayout.orientation = LinearLayout.VERTICAL

        val linearLayoutParams = LinearLayout.LayoutParams(
            cellWidth,
            resources.getDimensionPixelSize(R.dimen.grid_cell_layout_height)
        )

        linearLayout.layoutParams = linearLayoutParams
        linearLayout.gravity = Gravity.CENTER_VERTICAL

        if (today == weekday["date"]) {
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.setColor(ContextCompat.getColor(requireContext(), R.color.background))
            shape.cornerRadii = floatArrayOf(16f, 16f, 16f, 16f, 0f, 0f, 0f, 0f)
            ViewCompat.setBackground(linearLayout, shape)
        }

        val date = TextView(activity)
        val weekDay = TextView(activity)

        date.text = weekday["date"]
        weekDay.text = weekday["weekday"]

        date.typeface = Typeface.DEFAULT_BOLD
        date.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.large_font_size))
        date.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        weekDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.medium_font_size))
        weekDay.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

        val dateLayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        date.layoutParams = dateLayoutParams

        linearLayout.addView(date)
        linearLayout.addView(weekDay)

//        val randomColor = generateRandomColor()
//        date.setTextColor(randomColor)
//        weekDay.setTextColor(randomColor)

        weekDaysLayout.addView(linearLayout, linearLayoutParams)
    }

    private fun generateRandomColor(): Int {
        val rnd = java.util.Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}