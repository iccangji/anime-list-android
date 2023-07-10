package com.example.submissionjetpackcompose.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TopNavBar(
    modifier: Modifier,
    navigateBack : () -> Unit,
    title : String
){
    IconButton(
        onClick = navigateBack,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = modifier
                .height(24.dp),
            tint = Color.White
        )
    }
    Text(
        text = title,
        style = MaterialTheme.typography.h6.copy(
            color = MaterialTheme.colors.surface
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}