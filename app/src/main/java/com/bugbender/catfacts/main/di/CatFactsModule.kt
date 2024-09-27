package com.bugbender.catfacts.main.di

import com.bugbender.catfacts.catfacts.data.CatFactsRepository
import com.bugbender.catfacts.catfacts.presentation.LanguageStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class CatFactsModule {

    @Binds
    @ViewModelScoped
    abstract fun catFactsRepository(repository: CatFactsRepository.Base): CatFactsRepository

    @Binds
    @ViewModelScoped
    abstract fun languageStore(store: LanguageStore.Base): LanguageStore
}