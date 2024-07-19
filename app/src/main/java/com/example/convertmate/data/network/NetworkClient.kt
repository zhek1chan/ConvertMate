package com.example.convertmate.data.network

import com.example.convertmate.domain.Resource
import retrofit2.Response

interface NetworkClient {
    suspend fun <T> search(apiCall: suspend () -> Response<T>): Resource<T>
    //suspend fun <T> doRequest(): Resource<T>
    fun <T> error(message: String): Resource<T>
}