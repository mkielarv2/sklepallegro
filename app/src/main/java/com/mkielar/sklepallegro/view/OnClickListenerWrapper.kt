package com.mkielar.sklepallegro.view

import com.mkielar.sklepallegro.model.OfferDTO

data class OnClickListenerWrapper(
    val onClickListener: (OfferDTO) -> Unit
)