package com.bugbender.catfacts.searchfacts.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugbender.catfacts.R
import com.bugbender.catfacts.core.presentation.theme.CatFactsTheme

@Composable
fun CatFactsScreen(modifier: Modifier = Modifier) {
    var shouldShowProgress by rememberSaveable { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.cats_facts),
            style = MaterialTheme.typography.displayLarge,
        )
        Spacer(modifier = Modifier.weight(1.0f))

        if (shouldShowProgress) {
            LoadingData()
        } else {

        }

        Spacer(modifier = Modifier.weight(1.0f))
        Row {
            if (!shouldShowProgress) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.new_fact),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingData(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = StrokeCap.Round,
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading_data),
            style = MaterialTheme.typography.titleSmall,
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingDataPreview() {
    CatFactsTheme {
        LoadingData()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchFactsScreenPreview() {
    CatFactsTheme {
        CatFactsScreen(Modifier.fillMaxSize())
    }
}

