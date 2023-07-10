package com.example.submissionjetpackcompose.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissionjetpackcompose.R
import com.example.submissionjetpackcompose.data.UiState
import com.example.submissionjetpackcompose.data.entity.AnimeEntity
import com.example.submissionjetpackcompose.di.Injection
import com.example.submissionjetpackcompose.ui.components.AnimeItemCard
import com.example.submissionjetpackcompose.ui.components.Header
import com.example.submissionjetpackcompose.ui.view_model.AnimeListViewModel
import com.example.submissionjetpackcompose.ui.view_model.ViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AnimeListViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (Int, Boolean) -> Unit,
    navigateToAbout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(150.dp)
                    .shadow(16.dp)
            ){
                Header(navigateToAbout = navigateToAbout)
            }
        },
    ) {
        Column(
            modifier = modifier
                .padding(it)
        ) {
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        viewModel.getAllAnime()
                        HomeLoading()
                    }
                    is UiState.Success -> {
                        if(uiState.data.isNotEmpty()) {
                            HomeContent(
                                listItem = uiState.data,
                                modifier = modifier,
                                viewModel = viewModel,
                                navigateToDetail = navigateToDetail
                            )
                        }
                        else{
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
fun HomeContent(
    listItem: List<AnimeEntity>,
    modifier: Modifier = Modifier,
    viewModel: AnimeListViewModel,
    navigateToDetail: (Int, Boolean) -> Unit
){
    val emptyQuery by viewModel.emptyQuery
    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    if(emptyQuery) scope.launch { gridState.animateScrollToItem(0) }

    LazyVerticalGrid(
        state = gridState,
        columns = object : GridCells {
            override fun Density.calculateCrossAxisCellSizes(
                availableSize: Int,
                spacing: Int
            ): List<Int> {
                return listOf(
                    (availableSize / 2) - spacing,
                    (availableSize / 2) - spacing
                )
            }
        },
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        items(
            listItem,
            key = { it.id }) { data ->
            AnimeItemCard(
                modifier = Modifier.clickable {
                    navigateToDetail(data.id, data.favorite)
                },
                data.title,
                data.image,
                data.year,
            )
        }
    }
}

@Composable
fun HomeLoading(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier.fillMaxSize()){
        LinearProgressIndicator(
            color = MaterialTheme.colors.primary,
            backgroundColor = Color.LightGray,
            modifier = modifier
                .height(8.dp)
                .align(alignment = Center)
        )
    }
}

@Composable
fun HomeError(
    modifier: Modifier = Modifier,
    message: String
){
    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentHeight()
    ){
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = stringResource(id = R.string.no_data),
            tint = Color.Gray,
            modifier = modifier
                .height(24.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.subtitle2.copy(
                color = Color.Gray
            ),
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}