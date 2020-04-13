package com.mkielar.sklepallegro

import com.mkielar.sklepallegro.api.AllegroApiClient
import com.mkielar.sklepallegro.view.ListingAdapter
import com.mkielar.sklepallegro.viewmodel.ListingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {
    val koin = module {
        single { ListingAdapter() }
        single { AllegroApiClient.create() }
        viewModel { ListingViewModel(get(), true) }
    }
}