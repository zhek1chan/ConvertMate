package com.example.convertmate.data.network

import android.util.Log
import com.example.convertmate.domain.Resource
import retrofit2.Response
import java.lang.Exception

/**
 * This helps to properly handle the response gotten from the API - Be it error, success etc
 */

class RetrofitNetworkClient: NetworkClient {

    override suspend fun <T> search(apiCall: suspend () -> Response<T>): Resource<T> {

        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Resource.success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    /*
    override suspend fun <T> doRequest(): Resource<T> {
        try {
            val response = api.getCurrency()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Resource.success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }*/

    override fun <T> error(message: String): Resource<T> {
        Log.e("remoteDataSource", message)
        return Resource.error("Network failed by: $message")
    }
}