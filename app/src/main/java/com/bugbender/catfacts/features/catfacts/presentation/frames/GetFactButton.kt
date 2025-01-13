package com.bugbender.catfacts.features.catfacts.presentation.frames

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bugbender.catfacts.R

@Composable
fun GetFactButton(onGetFact: () -> Unit, modifier: Modifier = Modifier) {
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