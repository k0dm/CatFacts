package com.bugbender.catfacts.features.catfacts.data

import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactsAPI {

    @GET("https://meowfacts.herokuapp.com")
    suspend fun catFact(
        @Query("lang") language: String = "ukr"
    ): CatFactDTO
}