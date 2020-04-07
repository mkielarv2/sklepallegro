package com.mkielar.sklepallegro.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceDTO(
    val amount: String,
    val currency: String
) : Parcelable