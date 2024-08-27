package com.bugbender.catfacts.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bugbender.catfacts.core.presentation.theme.CatFactsTheme
import com.bugbender.catfacts.searchfacts.presentation.CatFactsScreen
import com.bugbender.catfacts.splash.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatFactsTheme {
                CatFactsApp()
            }
        }
    }
}

@Composable
fun CatFactsApp() {
    var shouldShowSplashScreen by rememberSaveable { mutableStateOf(true) }
    val modifier = Modifier.fillMaxSize()

    Scaffold(
        bottomBar = {if(!shouldShowSplashScreen) {} }
    ) { padding ->
        if (shouldShowSplashScreen) {
            SplashScreen(
                onFinish = { shouldShowSplashScreen = false },
                modifier = modifier
            )
        } else {
            CatFactsScreen(modifier = modifier.padding(padding))
        }
    }
}