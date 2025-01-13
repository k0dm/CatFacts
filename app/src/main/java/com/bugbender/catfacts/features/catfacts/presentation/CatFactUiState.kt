package com.bugbender.catfacts.features.catfacts.presentation

interface CatFactUiState {

    data class Success(val catFact: CatFactUi) : CatFactUiState

    data class Error(val message: String) : CatFactUiState

    object Loading : CatFactUiState

    object FirstLoading : CatFactUiState
}