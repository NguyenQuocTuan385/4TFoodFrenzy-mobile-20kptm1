package com.example.a4tfoodfrenzy.Api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NinjasApiService {
    @GET("v1/nutrition")
    fun getNutritionData(
        @Header("X-Api-Key") apiKey: String,
        @Query("query") ingr: String
    ): Call<List<Food>>

    companion object {
        fun create(): NinjasApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.api-ninjas.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // tạo đối tượng EdamamApiService
            return retrofit.create(NinjasApiService::class.java)
        }
    }

}