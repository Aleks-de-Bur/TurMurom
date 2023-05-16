package com.example.turmurom.retrofit

import com.example.turmurom.database.models.Category
import com.example.turmurom.database.models.ExampleAPIModel
import com.example.turmurom.database.models.Excursion
import com.example.turmurom.database.models.Guide
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExampleAPI {
//    @GET("products/{id}")
    @GET("catAPI/{id}")
    suspend fun getCategoryById(@Path("id") id: Long): Response<Category>

    @GET("guides")
    suspend fun getGuides(): Response<List<Guide>>

    @GET("excursions")
    suspend fun getExcursions(): Response<List<Excursion>>
}