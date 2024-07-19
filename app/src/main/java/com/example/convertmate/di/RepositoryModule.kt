package com.example.convertmate.di

import com.example.convertmate.data.impl.ConvertRepositoryImpl
import com.example.convertmate.data.impl.MainRepoImpl
import com.example.convertmate.data.network.NetworkClient
import com.example.convertmate.data.network.RetrofitNetworkClient
import com.example.convertmate.domain.repository.ConvertRepository
import com.example.convertmate.domain.repository.MainRepo
import org.koin.dsl.module

val repositoryModule = module {

    single<MainRepo> {
        MainRepoImpl(convertRepository = get(), networkClient = get())
    }

    single<ConvertRepository> {
        ConvertRepositoryImpl(currencyApi = get())
    }
}