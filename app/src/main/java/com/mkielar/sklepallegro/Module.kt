package com.mkielar.sklepallegro

import com.mkielar.sklepallegro.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {
    val koin = module {
        single { AllegroApiClient.create() }
        viewModel { MainViewModel(get()) }
    }
}