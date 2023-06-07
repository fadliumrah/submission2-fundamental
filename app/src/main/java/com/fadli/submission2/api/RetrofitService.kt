package com.fadli.submission2.api

import com.fadli.submission2.util.Constanta.API_URL
import okhttp3.OkHttpClient
import com.fadli.submission2.util.Constanta.GITHUB_TOKEN
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitService {

    private fun client(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                val original = it.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Authorization", GITHUB_TOKEN)
                val request = requestBuilder.build()
                it.proceed(request)
            }
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

    fun create(): ApiService =
        Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

}