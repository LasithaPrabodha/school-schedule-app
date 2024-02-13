package com.capstoneprojectg8.schoolscheduleapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.ClassItem

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Today Fragment"
    }
    val text: LiveData<String> = _text
    private val _classes = MutableLiveData<List<ClassItem>>().apply {

        value = listOf()
    }
    val classes: LiveData<List<ClassItem>> = _classes
    fun loadClassItems() {

        val dummyAssignments = listOf(
            Assignment("Homework 1", isChecked = false, note = "Chapter 3"),
            Assignment("Read book", isChecked = true, note = "Chapter 4")
        )

        val dummyList = listOf(
            ClassItem(
                startTime = "08:00 AM",
                endTime = "10.00 AM",
                courseCode = "MTH101",
                courseName = "Android",
                room = "Room 101",
                assignments = dummyAssignments,
                isCancelable = true,
                color = R.color.blue
            ),
            ClassItem(
                startTime = "10:00 AM",
                endTime = "12.00 PM",
                courseCode = "HST201",
                courseName = "IOS",
                room = "Room 201",
                assignments = dummyAssignments,
                isCancelable = false,
                color = R.color.green
            ),
//            ClassItem(
//                startTime = "01:00 PM",
//                endTime = "03.00 PM",
//                courseCode = "MTH102",
//                courseName = "Capstone Project",
//                room = "Room 221",
//                assignments = dummyAssignments,
//                isCancelable = true
//
//        ),
//            ClassItem(
//                startTime = "03:00 PM",
//                endTime = "05.00 PM",
//                courseCode = "MTH103",
//                courseName = "Capstone Project",
//                room = "Room 110",
//                assignments = dummyAssignments,
//                isCancelable = true
//            ),
//
//            ClassItem(
//                startTime = "05:00 PM",
//                endTime = "07.00 PM",
//                courseCode = "MTH103",
//                courseName = "Capstone Project",
//                room = "Room 110",
//                assignments = dummyAssignments,
//                isCancelable = true
//            ),
//
//            ClassItem(
//                startTime = "05:00 PM",
//                endTime = "07.00 PM",
//                courseCode = "MTH103",
//                courseName = "Capstone Project",
//                room = "Room 110",
//                assignments = dummyAssignments,
//                isCancelable = true
//            ),
            // Add more items...
        )
        _classes.value = dummyList
    }
}