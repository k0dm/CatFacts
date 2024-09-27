package com.bugbender.catfacts.catfacts.presentation.frames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugbender.catfacts.core.presentation.theme.CatFactsTheme

@Composable
fun ErrorFrame(
    errorMessage: String, onGetFact: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            text = errorMessage,
            fontSize = 24.sp,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Spacer(Modifier.weight(1f))

        GetFactButton(onGetFact = onGetFact, modifier = Modifier.padding(bottom = 64.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorFramePreview() {
    CatFactsTheme {
        ErrorFrame(
            errorMessage = "No Internet Connection !!! dsafsdafasdf",
            onGetFact = {}
        )
    }
}