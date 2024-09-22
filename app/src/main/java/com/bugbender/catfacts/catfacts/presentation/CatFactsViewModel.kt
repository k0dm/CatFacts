package com.bugbender.catfacts.catfacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugbender.catfacts.catfacts.data.CatFactsRepository
import com.bugbender.catfacts.catfacts.data.LoadResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatFactsViewModel @Inject constructor(
    private val catFactRepository: CatFactsRepository,
) : ViewModel() {

    var uiState by mutableStateOf<CatFactUiState>(CatFactUiState.FirstLoading)

    fun catFact() {
        uiState = CatFactUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = catFactRepository.catFact()

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
}

interface CatFactUiState {

    data class Success(val catFact: CatFactUi) : CatFactUiState

    data class Error(val message: String) : CatFactUiState

    object Loading : CatFactUiState

    object FirstLoading : CatFactUiState
}