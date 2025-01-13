package com.bugbender.catfacts.features.catfacts.data

import android.content.Context
import com.bugbender.catfacts.R
import com.bugbender.catfacts.features.catfacts.presentation.Language
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface LanguageStore {

    fun chosenLanguage(): String

    fun changeLanguage(country: String)

    class Base @Inject constructor(@ApplicationContext context: Context) : LanguageStore {
        private val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        private var currentChosenCountry = ""

        init {
            currentChosenCountry = sharedPreferences.getString(KEY, "US") ?: "US"
        }

        override fun chosenLanguage() = currentChosenCountry

        override fun changeLanguage(country: String) {
            sharedPreferences.edit().putString(KEY, country).apply()
        }

        companion object {
            private const val NAME = "language_config"
            private const val KEY = "language"
        }
    }
}