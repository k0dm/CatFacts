package com.bugbender.catfacts.searchfacts.data

import retrofit2.http.GET


interface CatFactsAPI {

    @GET
    fun catFact(): CatFactDTO
}