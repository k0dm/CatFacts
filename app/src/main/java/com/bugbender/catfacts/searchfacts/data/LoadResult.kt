package com.bugbender.catfacts.searchfacts.data

interface LoadResult {

    interface Mapper {

        fun mapSuccess(catFact: CatFact)

        fun mapError(message: String)
    }

    fun map(mapper: Mapper)

    data class Success(private val catFact: CatFact) : LoadResult {

        override fun map(mapper: Mapper) = mapper.mapSuccess(catFact = catFact)
    }

    data class Error(private val message: String) : LoadResult {

        override fun map(mapper: Mapper) = mapper.mapError(message)
    }
}