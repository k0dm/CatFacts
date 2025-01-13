package com.bugbender.catfacts.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bugbender.catfacts.features.catfacts.presentation.CatFactsScreen
import com.bugbender.catfacts.features.favorites.presentation.FavoritesScreen

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = CatFactsRoute,
        modifier = modifier
    ) {
        composable<CatFactsRoute> {
            CatFactsScreen()
        }
        composable<FavoritesRoute> {
            FavoritesScreen(navigateToCatFacts = {
                navController.navigateToSingleTop(CatFactsRoute)
            })
        }
    }
}

fun NavHostController.navigateToSingleTop(route: Route) {
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(this@navigateToSingleTop.graph.findStartDestination().id) {
           saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        restoreState = true
    }
}