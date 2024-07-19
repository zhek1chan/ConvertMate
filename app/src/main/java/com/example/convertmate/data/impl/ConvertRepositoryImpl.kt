package com.example.convertmate.data.impl

import com.example.convertmate.data.network.CurrencyApi
import com.example.convertmate.data.network.dto.CurrencyDto
import com.example.convertmate.domain.repository.ConvertRepository
import retrofit2.Response

class ConvertRepositoryImpl(private val currencyApi: CurrencyApi): ConvertRepository {

    override suspend fun getConvertedRate(accessKey: String, from: String, to: String, amount: Double) =
        currencyApi.convertCurrency(accessKey, from, to, amount)

    override suspend fun getCurrencies(accessKey: String): Response<CurrencyDto> {
        return currencyApi.getCurrency(accessKey)
    }
}