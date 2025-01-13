package com.bugbender.catfacts.features.catfacts.data

sealed interface LoadResult {

    interface Mapper { //todo use mapper

        fun mapSuccess(catFact: CatFact)

        fun mapError(message: String)
    }

    fun map(mapper: Mapper)

    data class Success(val catFact: CatFact) : LoadResult {

        override fun map(mapper: Mapper) = mapper.mapSuccess(catFact = catFact)
    }

    data class Error(val message: String) : LoadResult {

        override fun map(mapper: Mapper) = mapper.mapError(message)
    }
}