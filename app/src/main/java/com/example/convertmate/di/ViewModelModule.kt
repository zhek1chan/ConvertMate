package com.example.convertmate.di

import com.example.convertmate.presentation.viewmodel.ChooseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        ChooseViewModel(rep = get())
    }
}