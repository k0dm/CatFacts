package com.bugbender.catfacts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bugbender.catfacts.R
import com.bugbender.catfacts.features.catfacts.presentation.CatFactsScreen
import com.bugbender.catfacts.features.favorites.presentation.FavoritesScreen
import com.bugbender.catfacts.ui.navigation.AppNavBar
import com.bugbender.catfacts.ui.navigation.AppNavHost
import com.bugbender.catfacts.ui.navigation.CatFactsRoute
import com.bugbender.catfacts.ui.navigation.FavoritesRoute
import com.bugbender.catfacts.ui.theme.CatFactsTheme

import com.bugbender.catfacts.ui.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatFactsTheme {
                var shouldShowSplashScreen by rememberSaveable { mutableStateOf(true) }

                SplashScreen(onFinish = { shouldShowSplashScreen = false })
                if (!shouldShowSplashScreen) {
                    CatFactsApp()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CatFactsApp() {
    val navController = rememberNavController()

    Scaffold(
        content = { paddingValues ->
            AppNavHost(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        },
        bottomBar = {
            AppNavBar(navController)
        }
    )
}