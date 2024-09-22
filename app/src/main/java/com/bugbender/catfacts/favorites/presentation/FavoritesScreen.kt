package com.bugbender.catfacts.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugbender.catfacts.R
import com.bugbender.catfacts.core.presentation.theme.CatFactsTheme

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {

}

@Composable
fun FavoritesListFrame(modifier: Modifier = Modifier) {
    LazyColumn {

    }
}

@Composable
fun NoSavedFactsFrame() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.no_saved_facts),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {
                //todo navigate to Facts
            },
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Go to facts", fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoSavedFactsFramePreview() {
    CatFactsTheme {
        NoSavedFactsFrame()
    }
}