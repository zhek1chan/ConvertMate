package com.example.convertmate.domain.repository

import com.example.convertmate.domain.Resource
import com.example.convertmate.data.network.dto.ConvertDto
import com.example.convertmate.data.network.dto.CurrencyDto
import kotlinx.coroutines.flow.Flow


interface MainRepo {

    suspend fun getConvertedData(
        accessKey: String,
        from: String,
        to: String,
        amount: Double
    ): Flow<Resource<ConvertDto>>

    suspend fun getCurrencies(accessKey: String): Flow<Resource<CurrencyDto>>
}