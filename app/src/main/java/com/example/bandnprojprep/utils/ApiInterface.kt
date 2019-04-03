package com.example.bandnprojprep.utils

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import BooksInfo
import retrofit2.converter.gson.GsonConverterFactory


interface ApiInterface {

    @GET("volumes")
    fun getBooksInfo(): Call<BooksInfo>

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