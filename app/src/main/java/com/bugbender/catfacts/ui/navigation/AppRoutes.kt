package com.bugbender.catfacts.ui.navigation

import kotlinx.serialization.Serializable

interface Route

@Serializable
data object CatFactsRoute: Route

@Serializable
data object FavoritesRoute: Route