package com.bugbender.catfacts.catfacts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bugbender.catfacts.R
import com.bugbender.catfacts.core.presentation.theme.CatFactsTheme

@Composable
fun CatFactsScreen(
    modifier: Modifier = Modifier,
    viewModel: CatFactsViewModel = viewModel()
) {

    CatFactsContainer(
        state = viewModel.uiState,
        onGetFact = viewModel::catFact,
        modifier = modifier
    )
}

@Composable
fun CatFactsContainer(
    state: CatFactUiState,
    onGetFact: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.cats_facts),
            style = MaterialTheme.typography.displayLarge,
        )

        //state.show() todo figure out how to through viewModel

        when (state) {
            is CatFactUiState.FirstLoading -> {
                LoadingDataFrame()
                onGetFact()
            }

            is CatFactUiState.Loading -> LoadingDataFrame()

            is CatFactUiState.Error -> ErrorFrame(
                errorMessage = state.message,
                onGetFact = onGetFact
            )

            is CatFactUiState.Success -> CatFactFrame(fact = state.fact, onGetFact = onGetFact)
        }

    }
}


@Preview(showSystemUi = true)
@Composable
private fun SearchFactsScreenPreview() {
    CatFactsTheme {
        CatFactsContainer(state = CatFactUiState.Loading)
    }
}


@Composable
fun GetFactButton(onGetFact: () -> Unit, modifier: Modifier = Modifier) {
    Row {
        Button(
            onClick = onGetFact,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.new_fact),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun LoadingDataFrame() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
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
            modifier = Modifier.padding(horizontal = 32.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingDataFramePreview() {
    CatFactsTheme {
        LoadingDataFrame()
    }
}

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


@Composable
fun CatFactFrame(fact: String, onGetFact: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            text = fact,
            fontSize = 24.sp,
            color = Color.Black,
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
private fun CatFactFramePreview() {
    CatFactsTheme {
        CatFactFrame(
            fact = "\"Cats must have fat in their\n" +
                    "diet because they cannot produce it on their own.\"",
            onGetFact = {}
        )
    }
}