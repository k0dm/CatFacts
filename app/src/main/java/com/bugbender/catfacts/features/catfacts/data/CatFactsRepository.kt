package com.bugbender.catfacts.features.catfacts.data

import com.bugbender.catfacts.features.favorites.data.CatFactEntity
import com.bugbender.catfacts.features.favorites.data.FavoritesCacheDataSource
import javax.inject.Inject

interface CatFactsRepository {

    suspend fun catFact(language: String): LoadResult

    suspend fun addToFavorites(text: String)

    suspend fun deleteFromFavorites(text: String)

    class Base @Inject constructor(
        private val api: CatFactsAPI,
        private val cacheDataSource: FavoritesCacheDataSource
    ) : CatFactsRepository {

        override suspend fun catFact(language: String): LoadResult {
            return try {
                val catFactDTO = api.catFact(language)
                val data = catFactDTO.data[0]
                val isFavorite = cacheDataSource.contains(text = data)
                LoadResult.Success(catFact = CatFact(data = data, isFavorite = isFavorite))
            } catch (e: Exception) {
                LoadResult.Error(message = e.message ?: "Error")
            }
        }

        override suspend fun addToFavorites(text: String) {
            cacheDataSource.addCatFact(CatFactEntity(text = text))
        }

        override suspend fun deleteFromFavorites(text: String) {
            cacheDataSource.deleteCatFact(CatFactEntity(text = text))
        }
    }
}