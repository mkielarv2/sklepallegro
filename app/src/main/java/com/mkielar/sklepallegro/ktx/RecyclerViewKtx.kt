package com.mkielar.sklepallegro.ktx

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    context.getDrawable(drawableRes)?.let { drawable ->
        addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            ).apply {
                setDrawable(drawable)
            }
        )
    }
}