package com.bugbender.catfacts.favorites.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavoritesRepository {

    fun allFacts(): Flow<List<CatFactEntity>>

    suspend fun delete(catFact: FavoriteFact)

    class Base @Inject constructor(
        private val cacheDataSource: FavoritesCacheDataSource
    ) : FavoritesRepository {

        override fun allFacts(): Flow<List<CatFactEntity>> {
            return cacheDataSource.getAllFacts()
        }

        override suspend fun delete(catFact: FavoriteFact) {
            cacheDataSource.deleteCatFact(CatFactEntity(text = catFact.text))
        }
    }
}