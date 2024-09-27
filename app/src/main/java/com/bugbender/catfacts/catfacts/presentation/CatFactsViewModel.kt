package com.bugbender.catfacts.catfacts.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugbender.catfacts.R
import com.bugbender.catfacts.catfacts.data.CatFactsRepository
import com.bugbender.catfacts.catfacts.data.LoadResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatFactsViewModel @Inject constructor(
    private val catFactRepository: CatFactsRepository,
    private val languageStore: LanguageStore
) : ViewModel() {

    var uiState by mutableStateOf<CatFactUiState>(CatFactUiState.FirstLoading)

    val languages: SnapshotStateList<Language> = mutableStateListOf(
        Language("US", "eng", R.drawable.flag_us),
        Language("CZ", "cze", R.drawable.flag_cz),
        Language("DE", "ger", R.drawable.flag_de),
        Language("ES", "esp", R.drawable.flag_es),
        Language("RU", "rus", R.drawable.flag_ru),
        Language("PT", "por", R.drawable.flag_pt),
        Language("PH", "fil", R.drawable.flag_ph),
        Language("UA", "ukr", R.drawable.flag_ua),
        Language("IT", "ita", R.drawable.flag_it),
        Language("TW", "zho", R.drawable.flag_tw),
        Language("KO", "kor", R.drawable.flag_ko),
    )
    private var currentChosenLanguageIndex = languages.indexOfFirst { it.chosen }

    init {
        currentChosenLanguageIndex =
            languages.indexOfFirst { it.country == languageStore.chosenLanguage() }
        if (currentChosenLanguageIndex == -1) currentChosenLanguageIndex = 0
        languages[currentChosenLanguageIndex] =
            languages[currentChosenLanguageIndex].copy(chosen = true)
    }

    fun catFact() {
        uiState = CatFactUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                catFactRepository.catFact(language = languages[currentChosenLanguageIndex].iso)

            withContext(Dispatchers.Main) {
                uiState = when (result) {
                    is LoadResult.Success -> CatFactUiState.Success(
                        CatFactUi(
                            text = result.catFact.data,
                            isFavorite = result.catFact.isFavorite
                        )
                    )

                    is LoadResult.Error -> CatFactUiState.Error(message = result.message)
                }
            }
        }
    }

    fun addToFavorites(text: String) = viewModelScope.launch(Dispatchers.IO) {
        catFactRepository.addToFavorites(text)
        withContext(Dispatchers.Main) {
            uiState = CatFactUiState.Success(CatFactUi(text = text, isFavorite = true))
        }
    }

    fun deleteFromFavorites(text: String) = viewModelScope.launch(Dispatchers.IO) {
        catFactRepository.deleteFromFavorites(text)
        withContext(Dispatchers.Main) {
            uiState = CatFactUiState.Success(CatFactUi(text = text, isFavorite = false))
        }
    }

    fun changeLanguage(language: Language) {
        languages[currentChosenLanguageIndex] =
            languages[currentChosenLanguageIndex].copy(chosen = false)

        val newLanguageIndex = languages.indexOf(language)
        languages[newLanguageIndex] = languages[newLanguageIndex].copy(chosen = true)
        currentChosenLanguageIndex = newLanguageIndex
        languageStore.saveLanguage(language = language.country)
    }
}

interface LanguageStore {

    fun chosenLanguage(): String

    fun saveLanguage(language: String)

    class Base @Inject constructor(@ApplicationContext context: Context) : LanguageStore {
        private val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

        companion object {
            const val NAME = "language_config"
            const val KEY = "language"
        }

        override fun chosenLanguage() = sharedPreferences.getString(KEY, "") ?: ""

        override fun saveLanguage(language: String) {
            sharedPreferences.edit().putString(KEY, language).apply()
        }
    }
}