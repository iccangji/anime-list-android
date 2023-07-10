package com.example.submissionjetpackcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissionjetpackcompose.R
import com.example.submissionjetpackcompose.di.Injection
import com.example.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme
import com.example.submissionjetpackcompose.ui.view_model.AnimeListViewModel
import com.example.submissionjetpackcompose.ui.view_model.ViewModelFactory

@Composable
fun Header(
    modifier: Modifier = Modifier,
    viewModel: AnimeListViewModel = viewModel(
            factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToAbout: () -> Unit,
){
    val query by viewModel.query
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.primary)
    ){
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.app_name),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.surface,
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "about_page",
                    tint = Color.White,
                    modifier = modifier
                        .padding(end = 10.dp)
                        .clickable { navigateToAbout() }
                )
            }
            SearchBar(
                modifier = Modifier.background(MaterialTheme.colors.primary),
                query = query,
                onQueryChanged = viewModel::search,
                placeholder = stringResource(R.string.placeholder_search_anime)
            )
        }
    }
}