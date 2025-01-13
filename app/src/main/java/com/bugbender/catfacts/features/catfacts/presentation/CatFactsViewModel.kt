package com.bugbender.catfacts.features.catfacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugbender.catfacts.R
import com.bugbender.catfacts.features.catfacts.data.CatFactsRepository
import com.bugbender.catfacts.features.catfacts.data.LanguageStore
import com.bugbender.catfacts.features.catfacts.data.LoadResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatFactsViewModel @Inject constructor(
    private val catFactRepository: CatFactsRepository,
    private val languageStore: LanguageStore
) : ViewModel() {

    var uiState by mutableStateOf<CatFactUiState>(CatFactUiState.FirstLoading)
        private set

    private val _languagesFlow = MutableStateFlow<ArrayList<Language>>(
        arrayListOf(
            Language("US", "eng", R.drawable.flag_us),
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
    )
    val languagesFlow: StateFlow<List<Language>> = _languagesFlow
    private lateinit var chosenLanguage: Language
    private var chosenLanguageIndex = 0

    init {
        val currentChosenCountry = languageStore.chosenLanguage()
        _languagesFlow.update { newList ->
            chosenLanguageIndex = newList.indexOfFirst { it.country == currentChosenCountry }
            newList.also { languages ->
                chosenLanguage = languages[chosenLanguageIndex].copy(chosen = true)
                languages[chosenLanguageIndex] = chosenLanguage
            }
        }
    }

    fun catFact() {
        uiState = CatFactUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val result = catFactRepository.catFact(language = chosenLanguage.iso)

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
        _languagesFlow.update { newList ->
            newList.also { languages ->
                languages[chosenLanguageIndex] = languages[chosenLanguageIndex].copy(chosen = false)
                chosenLanguageIndex = languages.indexOfFirst { it == language }
                chosenLanguage = languages[chosenLanguageIndex].copy(chosen = true)
                languages[chosenLanguageIndex] = chosenLanguage

            }
        }

        languageStore.changeLanguage(country = language.country)
    }
}

