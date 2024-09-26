package com.bugbender.catfacts.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bugbender.catfacts.core.navigation.NavigationHost
import com.bugbender.catfacts.core.navigation.Route
import com.bugbender.catfacts.core.navigation.rememberNavigation
import com.bugbender.catfacts.core.presentation.theme.CatFactsTheme
import com.bugbender.catfacts.splash.SplashScreen
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

    val navigation = rememberNavigation(Route.Search)

    Scaffold(
        content = { paddingValues ->
            NavigationHost(
                navigation = navigation,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        },
        bottomBar = {
            NavigationBar {

                Route.ROUTES.forEach { route ->

                    NavigationBarItem(
                        selected = navigation.currentRoute() == route,
                        onClick = {
                            navigation.restart(route)
                        },
                        icon = {
                            Icon(imageVector = route.icon, null)
                        },
                        label = { Text(text = stringResource(route.titleRes)) }
                    )
                }
            }
        }
    )
}