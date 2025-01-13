package com.bugbender.catfacts.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bugbender.catfacts.R
import com.bugbender.catfacts.ui.theme.CatFactsTheme

@Composable
fun AppNavBar(navController: NavHostController) {
    val entry by navController.currentBackStackEntryAsState()

    Column {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.surfaceVariant
        )

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ) {

            val navBarItemColors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.surfaceVariant,
                indicatorColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = MaterialTheme.colorScheme.surfaceVariant
            )
            NavigationBarItem(
                selected = entry?.destination?.hasRoute<CatFactsRoute>() == true,
                onClick = {
                    navController.navigateToSingleTop(CatFactsRoute)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_navigation_icon)
                    )
                },
                label = { Text(text = stringResource(R.string.search)) },
                colors = navBarItemColors
            )

            NavigationBarItem(
                selected = entry?.destination?.hasRoute<FavoritesRoute>() == true,
                onClick = {
                    navController.navigateToSingleTop(FavoritesRoute)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        stringResource(R.string.favorites_navigation_icon)
                    )
                },
                label = { Text(text = stringResource(R.string.favorites)) },
                colors = navBarItemColors
            )
        }
    }
}

@Preview
@Composable
private fun AppNavBarPreview() {
    CatFactsTheme {
        AppNavBar(navController = NavHostController(LocalContext.current))
    }
}