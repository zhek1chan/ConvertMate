package com.example.convertmate.data.network

import com.example.convertmate.data.Constants
import com.example.convertmate.data.network.dto.ConvertDto
import com.example.convertmate.data.network.dto.CurrencyDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET(Constants.CONVERT_URL)
    suspend fun convertCurrency(
        @Query("api_key") accessKey: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<ConvertDto>

    @GET(Constants.CURRENCY_URL)
    suspend fun getCurrency(
        @Query("api_key") accessKey: String
    ): Response<CurrencyDto>

}