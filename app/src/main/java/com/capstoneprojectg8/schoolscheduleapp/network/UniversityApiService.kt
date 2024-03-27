package com.capstoneprojectg8.schoolscheduleapp.network

import com.capstoneprojectg8.schoolscheduleapp.models.University
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversityApiService {
    @GET("search")
    suspend fun getUniversities(@Query("name") searchTerm: String) : Response<List<University>>

}