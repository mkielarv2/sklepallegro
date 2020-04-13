package com.mkielar.sklepallegro.util

import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mkielar.sklepallegro.BuildConfig
import java.nio.charset.StandardCharsets

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("loadData")
    fun loadImage(view: WebView, html: String?) {
        view.loadData(html, BuildConfig.MIME_TEXT_HTML, StandardCharsets.UTF_8.name())
    }
}