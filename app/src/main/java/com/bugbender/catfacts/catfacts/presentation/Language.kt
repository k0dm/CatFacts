package com.bugbender.catfacts.catfacts.presentation

import androidx.annotation.DrawableRes

data class Language(
    val country: String,
    val iso: String,
    @DrawableRes val iconRes: Int,
    val chosen: Boolean = false
)