package com.bugbender.catfacts.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugbender.catfacts.favorites.data.FavoriteFact
import com.bugbender.catfacts.favorites.data.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

    val facts = repository.allFacts()

    fun deleteFact(catFact: FavoriteFact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(catFact = catFact)
        }
    }
}