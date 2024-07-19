package com.example.convertmate.di

import com.example.convertmate.data.Constants
import com.example.convertmate.data.network.CurrencyApi
import com.example.convertmate.data.network.NetworkClient
import com.example.convertmate.data.network.RetrofitNetworkClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val networkModule = module {

    single<CurrencyApi> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .apply {
                        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    }
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient()
    }
}