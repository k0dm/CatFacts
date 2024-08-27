package com.bugbender.catfacts.main.di

import com.bugbender.catfacts.searchfacts.data.CatFactsRepository
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
}