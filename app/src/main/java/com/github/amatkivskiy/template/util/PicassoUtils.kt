package com.github.amatkivskiy.template.util

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.github.amatkivskiy.template.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

fun ImageView.loadUrl(
    url: String?,
    @DrawableRes placeholder: Int = R.mipmap.ic_launcher_round,
    transformations: List<Transformation> = emptyList()
) {

    Picasso.with(this.context)
        .load(url)
        .fit()
        .placeholder(placeholder)
        .transform(transformations)
        .into(this)
}