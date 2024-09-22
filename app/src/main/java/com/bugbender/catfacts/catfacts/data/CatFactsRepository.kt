package com.bugbender.catfacts.catfacts.data

import com.bugbender.catfacts.favorites.data.CatFactEntity
import com.bugbender.catfacts.favorites.data.FavoritesCacheDataSource
import javax.inject.Inject

interface CatFactsRepository {

    suspend fun catFact(): LoadResult

    suspend fun addToFavorites(text: String)

    suspend fun deleteFromFavorites(text: String)

    class Base @Inject constructor(
        private val api: CatFactsAPI,
        private val cacheDataSource: FavoritesCacheDataSource
    ) : CatFactsRepository {

        override suspend fun catFact(): LoadResult {
            return try {
                val catFactDTO = api.catFact()
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