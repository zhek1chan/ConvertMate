package com.example.convertmate.domain.repository

import com.example.convertmate.data.network.dto.ConvertDto
import com.example.convertmate.data.network.dto.CurrencyDto
import retrofit2.Response

interface ConvertRepository {
    suspend fun getConvertedRate(
        accessKey: String,
        from: String,
        to: String,
        amount: Double
    ): Response<ConvertDto>

    suspend fun getCurrencies(accessKey: String): Response<CurrencyDto>
}