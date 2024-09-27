package com.bugbender.catfacts.catfacts.presentation.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bugbender.catfacts.R
import com.bugbender.catfacts.catfacts.presentation.CatFactUi
import com.bugbender.catfacts.core.presentation.theme.CatFactsTheme

@Composable
fun CatFactFrame(
    catFact: CatFactUi, onGetFact: () -> Unit,
    onDeleteFromFavorites: (String) -> Unit,
    onAddToFavorites: (String) -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (factText, getFactButton, iconButton) = createRefs()
        val topGuide = createGuidelineFromTop(16.dp) // Optional to limit how far the text can go up
        val bottomGuide = createGuidelineFromBottom(64.dp)

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp)
                .constrainAs(factText) {
                    top.linkTo(topGuide)
                    centerVerticallyTo(parent)
                    centerHorizontallyTo(parent)
                    bottom.linkTo(getFactButton.top, margin = 16.dp)
                }
        ) {
            Text(
                text = catFact.text,
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier

            )
        }

        GetFactButton(
            onGetFact = onGetFact,
            modifier = Modifier
                .constrainAs(getFactButton) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(bottomGuide)
                }
        )

        IconButton(
            onClick = {
                if (catFact.isFavorite) {
                    onDeleteFromFavorites(catFact.text)
                } else {
                    onAddToFavorites(catFact.text)
                }
            },
            modifier = Modifier
                .constrainAs(iconButton) {
                    start.linkTo(getFactButton.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomGuide)
                }
        ) {
            Icon(
                imageVector = if (catFact.isFavorite)
                    Icons.Rounded.Favorite
                else Icons.Rounded.FavoriteBorder,
                contentDescription = stringResource(R.string.favorite_button),
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CatFactFramePreview() {
    CatFactsTheme {
        CatFactFrame(
            catFact = CatFactUi(
                text = "\"Cats must have fat in their\n" +
                        "diet because they cannot produce it on their own.\"",
                isFavorite = false
            ),
            onGetFact = {},
            onAddToFavorites = {},
            onDeleteFromFavorites = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CatFactFrameOverloadPreview() {
    CatFactsTheme {
        CatFactFrame(
            catFact = CatFactUi(
                text = "\"Cats must have fat in their\n" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"" +
                        "diet because they cannot produce it on their own.\"",
                isFavorite = false
            ),
            onGetFact = {},
            onAddToFavorites = {},
            onDeleteFromFavorites = {}
        )
    }
}