package com.bugbender.catfacts.features.favorites.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatFactDao {

    @Insert
    suspend fun addCatFact(catFact: CatFactEntity)

    @Delete
    suspend fun deleteCatFact(catFact: CatFactEntity)

    @Query("SELECT * FROM cat_fact")
    fun allCatFacts(): Flow<List<CatFactEntity>>

    @Query("SELECT * FROM CAT_FACT WHERE text = :text")
    suspend fun findByText(text: String): List<CatFactEntity>
}