package com.mkielar.sklepallegro.model

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class OfferBindingHelper(
    val offerDTO: OfferDTO,
    val onClickListener: (OfferDTO) -> Unit
) {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            Glide.with(view)
                .load(url)
                .into(view)
        }
    }
}