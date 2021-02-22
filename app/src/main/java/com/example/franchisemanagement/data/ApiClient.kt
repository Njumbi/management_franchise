package com.example.franchisemanagement.data

import com.example.franchisemanagement.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class ApiClient {

    var service: ApiService? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create( ApiService::class.java )
    }

}