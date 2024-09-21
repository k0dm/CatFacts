package com.bugbender.catfacts.catfacts.data

import javax.inject.Inject

interface CatFactsRepository {

    suspend fun catFact(): LoadResult

    class Base @Inject constructor(private val api: CatFactsAPI) : CatFactsRepository {

        override suspend fun catFact(): LoadResult {
            return try {
                val catFactDTO = api.catFact()
                LoadResult.Success(catFact = CatFact(data = catFactDTO.data[0]))
            } catch (e: Exception) {
                LoadResult.Error(message = e.message ?: "Error")
            }
        }
    }
}