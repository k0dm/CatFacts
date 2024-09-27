package com.bugbender.catfacts.catfacts.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bugbender.catfacts.R
import com.bugbender.catfacts.catfacts.presentation.frames.CatFactFrame
import com.bugbender.catfacts.catfacts.presentation.frames.ErrorFrame
import com.bugbender.catfacts.catfacts.presentation.frames.LoadingDataFrame
import com.bugbender.catfacts.core.presentation.theme.CatFactsTheme

@Composable
fun CatFactsScreen(
    modifier: Modifier = Modifier,
    viewModel: CatFactsViewModel = viewModel()
) {

    CatFactsContent(
        state = viewModel.uiState,
        languages = viewModel.languages,
        onChangeLanguage = viewModel::changeLanguage,
        onGetFact = viewModel::catFact,
        onAddToFavorites = viewModel::addToFavorites,
        onDeleteFromFavorites = viewModel::deleteFromFavorites,
        modifier = modifier
    )
}

@Composable
fun CatFactsContent(
    state: CatFactUiState,
    languages: List<Language> = listOf(),
    onChangeLanguage: (language: Language) -> Unit = {},
    onGetFact: () -> Unit = {},
    onAddToFavorites: (String) -> Unit = {},
    onDeleteFromFavorites: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {

    var showLanguageDropdownMenu by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

            val (textRef, iconRef, dropdownMenuRef) = createRefs()

            Text(
                text = stringResource(R.string.cats_facts),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.constrainAs(textRef) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }
            )
            Box(modifier = Modifier.constrainAs(iconRef) {
                centerVerticallyTo(parent)
                linkTo(parent.start, parent.end, bias = 1f, endMargin = 8.dp)
            }) {
                IconButton(onClick = { showLanguageDropdownMenu = true }) {
                    Icon(
                        painter = painterResource(R.drawable.language),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = Color.Unspecified
                    )
                }

                DropdownMenu(
                    expanded = showLanguageDropdownMenu,
                    onDismissRequest = { showLanguageDropdownMenu = false },
                    modifier = Modifier.background(Color(242, 242, 242))
                ) {
                    languages.forEach { language ->
                        DropdownMenuItem(
                            text = {
                                Text(text = language.country, fontSize = 24.sp)
                            },
                            onClick = {
                                onChangeLanguage(language)
                                showLanguageDropdownMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(language.iconRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp),
                                    tint = Color.Unspecified
                                )
                            },
                            trailingIcon = {
                                if (language.chosen) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }

        }

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

            is CatFactUiState.Success -> CatFactFrame(
                catFact = state.catFact,
                onGetFact = onGetFact,
                onAddToFavorites = onAddToFavorites,
                onDeleteFromFavorites = onDeleteFromFavorites
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchFactsScreenPreview() {
    CatFactsTheme {
        CatFactsContent(state = CatFactUiState.Success(catFact = CatFactUi(text = "Hello", true)))
    }
}