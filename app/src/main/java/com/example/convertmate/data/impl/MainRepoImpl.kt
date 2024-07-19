package com.example.convertmate.data.impl

import com.example.convertmate.domain.Resource
import com.example.convertmate.data.network.dto.ConvertDto
import com.example.convertmate.domain.repository.ConvertRepository
import com.example.convertmate.data.network.dto.CurrencyDto
import com.example.convertmate.data.network.NetworkClient
import com.example.convertmate.domain.repository.MainRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepoImpl(
    private val convertRepository: ConvertRepository,
    private val networkClient: NetworkClient
) : MainRepo {

    override suspend fun getConvertedData(
        accessKey: String,
        from: String,
        to: String,
        amount: Double
    ): Flow<Resource<ConvertDto>> {
        return flow {
            emit(networkClient.search {
                convertRepository.getConvertedRate(
                    accessKey,
                    from,
                    to,
                    amount
                )
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrencies(accessKey: String): Flow<Resource<CurrencyDto>> {
        return flow {
            emit(networkClient.search { convertRepository.getCurrencies(accessKey) })
        }.flowOn(Dispatchers.IO)
    }
}