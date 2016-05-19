package com.valoplus.android.service.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by tom on 23.03.16.
 */
object RetrofitClient {

    fun getValoPlusClientForUrl(url: String): ValoPlusRestClient {
        val client = Retrofit.Builder()
                .baseUrl("https://$url")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return client.create(ValoPlusRestClient::class.java)
    }
}