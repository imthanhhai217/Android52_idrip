package com.vndevpro.android52_idrip.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object BindingAdapter {

    @androidx.databinding.BindingAdapter("urlImage")
    @JvmStatic
    fun ImageView.loadImageUrl(url: String) {
        if (url != null) {
            Glide.with(context).load(url).into(this)
        }
    }
}