package com.bugbender.catfacts.main.di

import com.bugbender.catfacts.favorites.data.FavoritesCacheDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoritesModule {

    @Binds
    abstract fun cacheDataSource(cacheDataSource: FavoritesCacheDataSource.Base): FavoritesCacheDataSource
}