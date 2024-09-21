package com.bugbender.catfacts.catfacts.data

import retrofit2.http.GET


interface CatFactsAPI {

    @GET("https://meowfacts.herokuapp.com")
    suspend fun catFact(): CatFactDTO
}