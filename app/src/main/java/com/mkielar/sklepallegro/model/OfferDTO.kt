package com.mkielar.sklepallegro.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferDTO(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val price: PriceDTO,
    val description: String
) : Parcelable