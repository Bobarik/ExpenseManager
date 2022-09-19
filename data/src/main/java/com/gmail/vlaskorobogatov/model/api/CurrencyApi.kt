package com.gmail.vlaskorobogatov.model.api

import com.gmail.vlaskorobogatov.model.entity.CurrencyApiResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("/api/latest.json")
    suspend fun getLatest(
        @Query("app_id") appId: String = "f9e7ce5f49324131a424885bc32756a0",
        @Query("prettyprint") prettyPrint: Boolean = false
    ): Response<CurrencyApiResponse>

    companion object {
        private const val baseUrl = "https://openexchangerates.org"

        fun create(): CurrencyApi {

            val client = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CurrencyApi::class.java)
        }
    }
}
