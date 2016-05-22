package com.valoplus.android.service.rest

import com.valoplus.android.service.ClientId
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient.Builder
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Created by tom on 23.03.16.
 */
object RetrofitClient {

    fun getValoPlusClientForUrl(url: String): ValoPlusRestClient {
        val httpClient = Builder();
        val interceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Chain): Response {
                val original = chain.request();

                val request = original.newBuilder()
                        .header("Authorization", ClientId.get())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        }
        httpClient.addInterceptor(interceptor);
        val okHttpClient = httpClient.build();

        val client = Retrofit.Builder()
                .baseUrl("http://$url")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        return client.create(ValoPlusRestClient::class.java)
    }
}