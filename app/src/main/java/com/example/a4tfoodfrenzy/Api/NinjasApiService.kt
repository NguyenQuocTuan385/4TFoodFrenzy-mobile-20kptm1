package com.example.a4tfoodfrenzy.Api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NinjasApiService {
    @GET("v1/nutrition")
    fun getNutritionData(
        @Header("X-Api-Key") apiKey: String,
        @Query("query") ingr: String
    ): Call<List<Food>>

    @Headers(
        "X-RapidAPI-Key: bc8afbd301msh9d9761d6639c953p122340jsnd47d709d08cf",
        "X-RapidAPI-Host: community-purgomalum.p.rapidapi.com",
    )
    @GET("containsprofanity")
    suspend fun getProfanityResult(@Query("text") checkingText: String): Response<Any>

    companion object {
        val PROFANITY_URL = "https://community-purgomalum.p.rapidapi.com/"
        val TRANSLATE_URL =
            "https://microsoft-translator-text.p.rapidapi.com/translate?to%5B0%5D=@toLanguage&api-version=3.0&from=@fromLanguage&profanityAction=NoAction&textType=plain"

        fun create(): NinjasApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.api-ninjas.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // tạo đối tượng EdamamApiService
            return retrofit.create(NinjasApiService::class.java)
        }

        fun createProfanityCheck(): NinjasApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PROFANITY_URL).build()

            return retrofit.create(NinjasApiService::class.java)
        }

        fun createTranslateRequest(
            textToTranslate: String,
            fromLanguage: String = "vi",
            toLanguage: String = "en"
        ): Request {
            // add from-to language to base url string
            var baseTranslateUrl = TRANSLATE_URL.replace("@fromLanguage", fromLanguage)
            baseTranslateUrl = baseTranslateUrl.replace("@toLanguage", toLanguage)

            val mediaType = "application/json".toMediaTypeOrNull()
            val body = RequestBody.create(
                mediaType,
                "[\r {\r\"Text\": \"$textToTranslate\"\r }\r]"
            )

            return Request.Builder()
                .url(baseTranslateUrl)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", "bc8afbd301msh9d9761d6639c953p122340jsnd47d709d08cf")
                .addHeader("X-RapidAPI-Host", "microsoft-translator-text.p.rapidapi.com")
                .build()
        }
    }

}