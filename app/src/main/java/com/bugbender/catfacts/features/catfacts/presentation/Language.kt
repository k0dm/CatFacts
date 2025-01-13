package com.bugbender.catfacts.features.catfacts.presentation

import androidx.annotation.DrawableRes

data class Language(
    val country: String,
    val iso: String,
    @DrawableRes val iconRes: Int,
    val chosen: Boolean = false
)