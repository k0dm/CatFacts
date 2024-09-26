package com.bugbender.catfacts.favorites.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavoritesCacheDataSource {

    fun getAllFacts(): Flow<List<CatFactEntity>>

    suspend fun addCatFact(catFact: CatFactEntity)

    suspend fun deleteCatFact(catFact: CatFactEntity)

    suspend fun contains(text: String): Boolean

    class Base @Inject constructor(private val dao: CatFactDao) : FavoritesCacheDataSource {

        override fun getAllFacts() = dao.allCatFacts()

        override suspend fun addCatFact(catFact: CatFactEntity) = dao.addCatFact(catFact)

        override suspend fun deleteCatFact(catFact: CatFactEntity) = dao.deleteCatFact(catFact)

        override suspend fun contains(text: String) = dao.findByText(text = text).isNotEmpty()
    }
}