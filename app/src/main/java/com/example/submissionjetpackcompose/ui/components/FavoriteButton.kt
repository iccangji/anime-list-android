package com.example.submissionjetpackcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submissionjetpackcompose.R
import com.example.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme

@Composable
fun FavoriteButton(
    modifier: Modifier
){
    Button(
        onClick = {},
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(16.dp)
    ) {
        Text(
            text = stringResource(R.string.add_favorite),
            modifier = modifier
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
    }
}