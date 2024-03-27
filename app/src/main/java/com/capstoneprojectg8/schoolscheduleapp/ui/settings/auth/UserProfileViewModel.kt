package com.capstoneprojectg8.schoolscheduleapp.ui.settings.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.models.University
import com.capstoneprojectg8.schoolscheduleapp.network.UniversityApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val universityApiService: UniversityApiService) :
    ViewModel() {


    private val _universities = MutableLiveData<UniversitySearchState>()
    val universities: LiveData<UniversitySearchState> = _universities

    sealed class UniversitySearchState {
        class Success(val data: List<University>) : UniversitySearchState()
        class Error(val throwable: Throwable) : UniversitySearchState()
        class Loading(val isLoading: Boolean) : UniversitySearchState()
    }


    fun searchUniversities(term: String) {
        viewModelScope.launch {
            try {
                updateState(UniversitySearchState.Loading(true))
                val response = universityApiService.getUniversities(term)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body() ?: emptyList()

                    updateState(UniversitySearchState.Success(result))
                } else {
                    updateState(UniversitySearchState.Error(Error("Error when fetching universities: ${response.code()}")))
                }
                updateState(UniversitySearchState.Loading(false))
            } catch (e: Exception) {
                updateState(UniversitySearchState.Error(e))
            }
        }
    }

    private fun updateState(state: UniversitySearchState) {
        _universities.value = state
    }
}