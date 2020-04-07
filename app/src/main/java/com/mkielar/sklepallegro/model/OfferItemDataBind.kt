package com.mkielar.sklepallegro.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class OfferItemDataBind(
    val name: String,
    val price: String,
    val currency: String,
    val thumbnailUrl: String
) {
    companion object {
        @JvmStatic
        @BindingAdapter("thumbnailUrl")
        fun loadImage(view: ImageView, url: String) {
            Glide.with(view)
                .load(url)
                .into(view)
        }
    }
}