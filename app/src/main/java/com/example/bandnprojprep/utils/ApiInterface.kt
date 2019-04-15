package com.example.bandnprojprep.utils

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import BooksInfo
import com.example.bandnprojprep.R
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query


interface ApiInterface {

    @GET("volumes")
    fun getBooksInfo(@Query("q") stringQuery: String, @Query("key") api_key:String = API_KEY): Call<BooksInfo>

    companion object Factory {
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}