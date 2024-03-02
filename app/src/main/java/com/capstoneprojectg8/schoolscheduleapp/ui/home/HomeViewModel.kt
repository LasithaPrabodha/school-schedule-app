package com.capstoneprojectg8.schoolscheduleapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.models.Assignments
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
            Assignments("Homework 1", isChecked = false, note = "Chapter 3"),
            Assignments("Read book", isChecked = true, note = "Chapter 4")
        )

        val dummyList = listOf(
            ClassItem(
                startTime = "08:00 AM",
                endTime = "10.00 AM",
                courseCode = "MTH101",
                courseName = "Android",
                room = "Room 101",
                assignments = dummyAssignments,
                color = R.color.blue
            ),
            ClassItem(
                startTime = "10:00 AM",
                endTime = "12.00 PM",
                courseCode = "HST201",
                courseName = "IOS",
                room = "Room 201",
                assignments = dummyAssignments,
                color = R.color.green
            ),

            ClassItem(
                startTime = "08:00 AM",
                endTime = "10.00 AM",
                courseCode = "MTH101",
                courseName = "Android",
                room = "Room 101",
                assignments = dummyAssignments,
                color = R.color.purple
            ),
            ClassItem(
                startTime = "08:00 AM",
                endTime = "10.00 AM",
                courseCode = "MTH101",
                courseName = "Android",
                room = "Room 201",
                assignments = dummyAssignments,
                color = R.color.green
            ),

            ClassItem(
                startTime = "08:00 AM",
                endTime = "10.00 AM",
                courseCode = "MTH101",
                courseName = "Capstone Project",
                room = "Room 111",
                assignments = dummyAssignments,
                color = R.color.blue
            ),

            ClassItem(
                startTime = "08:00 AM",
                endTime = "10.00 AM",
                courseCode = "MTH101",
                courseName = "IOS",
                room = "Room 211",
                assignments = dummyAssignments,
                color = R.color.red
            )
        )
        _classes.value = dummyList
    }
}