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
    private val repository: CatFactsRepository
) : ViewModel() {

    var uiState by mutableStateOf<CatFactUiState>(CatFactUiState.FirstLoading)

    fun catFact() {
        uiState = CatFactUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.catFact()

            withContext(Dispatchers.Main) {
                uiState = when (result) {
                    is LoadResult.Success -> CatFactUiState.Success(fact = result.catFact.data)
                    is LoadResult.Error -> CatFactUiState.Error(message = result.message)
                }
            }
        }
    }
}

interface CatFactUiState {

    data class Success(val fact: String) : CatFactUiState

    data class Error(val message: String) : CatFactUiState

    object Loading : CatFactUiState

    object FirstLoading : CatFactUiState
}