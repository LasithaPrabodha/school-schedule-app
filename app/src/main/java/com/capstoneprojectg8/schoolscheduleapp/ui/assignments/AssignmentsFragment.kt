package com.capstoneprojectg8.schoolscheduleapp.ui.assignments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.capstoneprojectg8.schoolscheduleapp.R

class AssignmentsFragment : Fragment() {

    companion object {
        fun newInstance() = AssignmentsFragment()
    }

    private val viewModel: AssignmentsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_assignments, container, false)
    }
}