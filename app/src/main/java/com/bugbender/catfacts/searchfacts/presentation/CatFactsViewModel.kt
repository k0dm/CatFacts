package com.bugbender.catfacts.searchfacts.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugbender.catfacts.searchfacts.data.CatFact
import com.bugbender.catfacts.searchfacts.data.CatFactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatFactsViewModel @Inject constructor(
    private val repository: CatFactsRepository
) : ViewModel() {

    private val _catFactViewModel = MutableLiveData<CatFact>()
    val catFactViewModel: LiveData<CatFact>
        get() = _catFactViewModel

    fun catFact() = viewModelScope.launch{
        repository.catFact().map()
    }
}