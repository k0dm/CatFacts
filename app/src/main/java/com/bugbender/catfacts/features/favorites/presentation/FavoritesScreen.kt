package com.bugbender.catfacts.features.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bugbender.catfacts.R
import com.bugbender.catfacts.ui.theme.CatFactsTheme
import com.bugbender.catfacts.features.favorites.data.FavoriteFact

@Composable
fun FavoritesScreen(navigateToCatFacts: () -> Unit) {
    val viewModel: FavoritesViewModel = hiltViewModel()
    val facts by viewModel.facts.collectAsStateWithLifecycle(listOf())

    FavoritesContent(
        facts = facts.map { FavoriteFact(text = it.text) },
        onGoToFacts = navigateToCatFacts,
        onDeleteItem = viewModel::deleteFact,
    )
}

@Composable
fun FavoritesContent(
    facts: List<FavoriteFact> = listOf(),
    onGoToFacts: () -> Unit = {},
    onDeleteItem: (catFact: FavoriteFact) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.favorites),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(8.dp)
        )
        if (facts.isEmpty()) {
            NoSavedFactsFrame(onGoToFacts = onGoToFacts)
        } else {
            Spacer(Modifier.height(16.dp))
            LazyColumn {
                items(facts) { item ->
                    FavoriteFactItem(catFact = item, onDeleteFact = onDeleteItem)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FavoritesContentPreview() {
    CatFactsTheme {
        FavoritesContent()
    }
}

@Preview(showSystemUi = true)
@Composable
fun NoSavedFactsFrame(onGoToFacts: () -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(text = stringResource(R.string.no_saved_facts), fontSize = 24.sp)
        Spacer(Modifier.height(32.dp))
        Button(onClick = onGoToFacts) {
            Text(text = stringResource(R.string.go_to_facts))
        }
    }
}


@Preview
@Composable
private fun FavoritesScreen() {
    CatFactsTheme {
        FavoritesScreen()
    }
}

@Preview(showSystemUi = true)
@Composable
fun FavoriteFactItem(
    catFact: FavoriteFact = FavoriteFact(text = "Of all the species of cats, the domestic cat is the only species able to hold its tail vertically while walking. All species of wild cats hold their talk horizontally or tucked between their legs while walking."),
    onShareFact: () -> Unit = {},
    onDeleteFact: (catFact: FavoriteFact) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(234, 234, 234)),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .padding(bottom = 8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = catFact.text,
                fontSize = 24.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onShareFact) {
                    Icon(painter = painterResource(R.drawable.share), contentDescription = null)
                }
                Spacer(Modifier.width(24.dp))
                IconButton(onClick = { onDeleteFact(catFact) }) {
                    Icon(painter = painterResource(R.drawable.close), contentDescription = null)
                }
            }
        }
    }
}