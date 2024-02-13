package com.capstoneprojectg8.schoolscheduleapp.ui.schedule

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentScheduleBinding
import com.capstoneprojectg8.schoolscheduleapp.models.ScheduleSlot
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHandler
import kotlinx.coroutines.Dispatchers


class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private var cellWidth: Int = 0
    private val today = DateHandler.getToday("dd")
    private lateinit var gridLayout: GridLayout
    private lateinit var weekDaysLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val scheduleViewModel =
//            ViewModelProvider(this).get(ScheduleViewModel::class.java)

        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val root: View = binding.root


        gridLayout = binding.scheduleGrid
        val displayMetrics = resources.displayMetrics
        var dpWidth = displayMetrics.widthPixels

        weekDaysLayout = binding.dayScroll
        val scheduleScroll = binding.scheduleScroll

        dpWidth -= weekDaysLayout.marginStart

        cellWidth = dpWidth / 5

        generateScheduleGrid()

        gridLayout.requestLayout()

        val dip = resources.getDimensionPixelSize(R.dimen.grid_cell_layout_height)

        scheduleScroll.post { scheduleScroll.scrollTo(0, dip * 9) }

        return root
    }

    private fun generateScheduleGrid() {
        val dayOfWeek = DateHandler.getWeekDates()

        for (i in 0 until dayOfWeek.size) {
            generateWeekDay(dayOfWeek[i])
        }

        for (row in 0 until gridLayout.rowCount) {
            generateHourLabel(gridLayout, row)
        }

        for (row in 0 until gridLayout.rowCount) {
            for (col in 1 until gridLayout.columnCount) {
                generateCells(gridLayout, row, col, dayOfWeek[col - 1])
            }
        }

        for (slot in DummySlots.getSlots()) {
            generateSlot(slot)
        }

    }


    private fun generateSlot(slot: ScheduleSlot) {

        val linearLayout = LinearLayout(activity)
        linearLayout.orientation = LinearLayout.VERTICAL

        val className = TextView(activity)
        val classRoom = TextView(activity)

        className.text = slot.className
        classRoom.text = slot.classRoom

        // set background rounded rectangle
        val shape = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.rounded_rectangle
        ) as GradientDrawable

        shape.setColor(ContextCompat.getColor(requireContext(), slot.color))
        ViewCompat.setBackground(linearLayout, shape)

        // Style the text views
        className.textSize = resources.getDimension(R.dimen.medium_font_size)
        className.setPadding(20, 25, 20, 0)
        className.typeface = Typeface.DEFAULT_BOLD
        val hourLayoutParams = LinearLayout.LayoutParams(
            resources.getDimensionPixelSize(R.dimen.grid_cell_layout_width),
            LayoutParams.WRAP_CONTENT
        )
        className.layoutParams = hourLayoutParams

        classRoom.textSize = resources.getDimension(R.dimen.small_font_size)
        classRoom.setPadding(20, 15, 20, 15)

        linearLayout.addView(className)
        linearLayout.addView(classRoom)

        val gridLayoutParams = generateGridLayoutParams(
            slot.row,
            slot.col,
            cellWidth - 30,
            resources.getDimensionPixelSize(R.dimen.grid_cell_layout_height) * slot.rowSpan - 30,
            slot.rowSpan
        )
        gridLayoutParams.setGravity(Gravity.CENTER)

        gridLayout.addView(linearLayout, gridLayoutParams)
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
        date.textSize = resources.getDimension(R.dimen.large_font_size)
        date.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        weekDay.textSize = resources.getDimension(R.dimen.medium_font_size)
        weekDay.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

        val dateLayoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        date.layoutParams = dateLayoutParams

        linearLayout.addView(date)
        linearLayout.addView(weekDay)

        weekDaysLayout.addView(linearLayout, linearLayoutParams)
    }

    private fun generateCells(
        gridLayout: GridLayout,
        row: Int,
        col: Int,
        weekday: Map<String, String>
    ) {
        val linearLayout = LinearLayout(activity)
        linearLayout.orientation = LinearLayout.VERTICAL

        // set column cell width/height
        val gridLayoutParams = generateGridLayoutParams(
            row,
            col,
            cellWidth,
            resources.getDimensionPixelSize(R.dimen.grid_cell_layout_height)
        )

        // set layout width/height/gravity
        val linearLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
        linearLayout.layoutParams = linearLayoutParams

        if (today == weekday["date"]) {
            val shape = GradientDrawable()
            shape.setColor(ContextCompat.getColor(requireContext(), R.color.background))
            ViewCompat.setBackground(linearLayout, shape)
        }

        //  border
        linearLayout.addView(generateBorder())

        gridLayout.addView(linearLayout, gridLayoutParams)
    }

    private fun generateBorder(): View {
        val borderView = View(context)
        borderView.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, 2
        )
        borderView.setBackgroundColor(Color.GRAY)
        return borderView
    }

    private fun generateHourLabel(gridLayout: GridLayout, row: Int) {
        val hour = TextView(activity)
        val amPm = TextView(activity)
        if (row == 0) {
            hour.text = getString(R.string._12)
        } else if (row < 10) {
            hour.text = getString(R.string._0, row.toString())
        } else if (row > 12) {
            if (row - 12 < 10) hour.text = getString(R.string._0, (row - 12).toString())
            else hour.text = (row - 12).toString()
        } else {
            hour.text = row.toString()
        }

        amPm.text = if (row < 12) "AM" else "PM"

        // Style the text views
        hour.textSize = resources.getDimension(R.dimen.medium_font_size)
        hour.setPadding(
            0,
            0,
            resources.getDimensionPixelSize(R.dimen.grid_time_indicator_padding),
            0
        )
        hour.typeface = Typeface.DEFAULT_BOLD
        hour.textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
        val hourLayoutParams = LinearLayout.LayoutParams(
            resources.getDimensionPixelSize(R.dimen.grid_time_indicator_layout_width),
            LayoutParams.WRAP_CONTENT
        )
        hour.layoutParams = hourLayoutParams

        amPm.textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
        amPm.textSize = resources.getDimension(R.dimen.small_font_size)
        amPm.setPadding(
            0,
            0,
            resources.getDimensionPixelSize(R.dimen.grid_time_indicator_padding),
            0
        )

        // Create the LinearLayout
        val linearLayout = LinearLayout(activity)
        linearLayout.orientation = LinearLayout.VERTICAL

        // Add the TextViews to the LinearLayout
        linearLayout.addView(generateBorder())
        linearLayout.addView(hour)
        linearLayout.addView(amPm)

        val gridLayoutParams = generateGridLayoutParams(
            row,
            0,
            resources.getDimensionPixelSize(R.dimen.grid_time_indicator_layout_width),
            resources.getDimensionPixelSize(R.dimen.grid_time_indicator_layout_height)
        )

        // Add the LinearLayout to the GridLayout
        gridLayout.addView(linearLayout, gridLayoutParams)
    }

    private fun generateGridLayoutParams(
        row: Int,
        col: Int,
        width: Int,
        height: Int,
        rowSpan: Int = 1
    ): GridLayout.LayoutParams {
        val gridLayoutParams = GridLayout.LayoutParams()
        if (rowSpan > 1) {
            gridLayoutParams.rowSpec = GridLayout.spec(row, rowSpan, 1f)
        } else {
            gridLayoutParams.rowSpec = GridLayout.spec(row)
        }
        gridLayoutParams.columnSpec = GridLayout.spec(col)
        gridLayoutParams.width = width
        gridLayoutParams.height = height
        return gridLayoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}