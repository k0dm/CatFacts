package com.bugbender.catfacts.features.catfacts.presentation.frames

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.bugbender.catfacts.features.catfacts.presentation.CatFactUi
import com.bugbender.catfacts.ui.theme.CatFactsTheme

@Composable
fun CatFactFrame(
    catFact: CatFactUi, onGetFact: () -> Unit,
    onDeleteFromFavorites: (String) -> Unit,
    onAddToFavorites: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = catFact.text,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState())
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp)
        ) {

            val (buttonRef, iconRef) = createRefs()

            GetFactButton(
                onGetFact = onGetFact,
                modifier = Modifier.constrainAs(buttonRef) {
                    centerHorizontallyTo(parent)
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
                modifier = Modifier.constrainAs(iconRef) {
                    start.linkTo(buttonRef.end)
                    end.linkTo(parent.end)
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

@Preview(showSystemUi = true)
@Composable
private fun CatFactFrameOverloadPreview() {
    CatFactsTheme {
        Surface(Modifier.systemBarsPadding()) {
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
}