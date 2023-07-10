package com.example.submissionjetpackcompose.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissionjetpackcompose.R
import com.example.submissionjetpackcompose.data.UiState
import com.example.submissionjetpackcompose.data.entity.AnimeEntity
import com.example.submissionjetpackcompose.di.Injection
import com.example.submissionjetpackcompose.ui.components.FavoriteItemCard
import com.example.submissionjetpackcompose.ui.components.SearchBar
import com.example.submissionjetpackcompose.ui.view_model.AnimeDetailViewModel
import com.example.submissionjetpackcompose.ui.view_model.FavoriteListViewModel
import com.example.submissionjetpackcompose.ui.view_model.ViewModelFactory

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteListViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (Int, Boolean) -> Unit
) {
    val query by viewModel.query

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(100.dp)
                    .shadow(16.dp)
            ){
                SearchBar(
                    query = query,
                    onQueryChanged = viewModel::search,
                    placeholder = stringResource(R.string.placeholder_search_favorite)
                )
            }
        }
    ) {
        Column(modifier = modifier.padding(it)) {
            val emptyQuery by viewModel.emptyQuery
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        viewModel.getFavoriteAnime()
                        HomeLoading()
                    }
                    is UiState.Success -> {
                        if (uiState.data.isNotEmpty()) {
                            FavoriteContent(
                                listItem = uiState.data,
                                modifier = modifier,
                                navigateToDetail = navigateToDetail,
                                viewModel = viewModel
                            )
                        } else {
                            HomeError(message = stringResource(R.string.no_data))
                        }
                    }
                    is UiState.Error -> {
                        HomeError(message = stringResource(R.string.no_data))
                    }
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FavoriteContent(
    listItem: List<AnimeEntity>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int, Boolean) -> Unit,
    viewModel: FavoriteListViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
){

    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        items(
            listItem,
            key = { it.id }) { data ->
            FavoriteItemCard(
                modifier = modifier,
                navigateToDetail = navigateToDetail,
                data,
                viewModel = viewModel
            )
        }
    }
}