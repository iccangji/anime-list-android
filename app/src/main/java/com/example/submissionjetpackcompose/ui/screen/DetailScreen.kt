package com.example.submissionjetpackcompose.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.submissionjetpackcompose.R
import com.example.submissionjetpackcompose.data.UiState
import com.example.submissionjetpackcompose.data.entity.AnimeEntity
import com.example.submissionjetpackcompose.di.Injection
import com.example.submissionjetpackcompose.model.DetailAnime
import com.example.submissionjetpackcompose.ui.components.TopNavBar
import com.example.submissionjetpackcompose.ui.view_model.AnimeDetailViewModel
import com.example.submissionjetpackcompose.ui.view_model.FavoriteListViewModel
import com.example.submissionjetpackcompose.ui.view_model.ViewModelFactory

@Composable
fun DetailScreen(
    anime: Int,
    favorite: Boolean,
    navigateBack: () -> Unit,
    navigateToFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AnimeDetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    favoriteViewModel: FavoriteListViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getDetailAnime(anime)
                HomeLoading()
                Log.d(
                    "Loading",
                    "Loading"
                )
            }
            is UiState.Success -> {
                Log.d(
                    "Success",
                    "Success"
                )
                if (uiState.data != null) {
                    DetailContent(
                        detailItem = uiState.data,
                        modifier = modifier,
                        navigateBack = navigateBack,
                        favorite = favorite,
                        favoriteViewModel = favoriteViewModel,
                        navigateToFavorite = navigateToFavorite
                    )
                } else {
                    HomeError(message = stringResource(R.string.no_data))
                }
            }
            is UiState.Error -> {
                HomeError(message = stringResource(R.string.error_connection))
            }
        }
    }
}

@Composable
fun DetailContent(
    detailItem: DetailAnime,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    favorite: Boolean,
    favoriteViewModel: FavoriteListViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToFavorite: () -> Unit
) {
    val textFavButton = if(favorite) stringResource(R.string.added_favorite)
    else stringResource(R.string.add_favorite)

    val favoriteItem = AnimeEntity(
        id = detailItem.id,
        title = detailItem.title,
        image = detailItem.image,
        year = detailItem.year,
        favorite = favorite
    )

    Scaffold(
    topBar = {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = modifier
                .shadow(16.dp)
        ) {
            TopNavBar(
                modifier = modifier,
                navigateBack = navigateBack,
                title = detailItem.title
            )
        }
    }
    ) { paddingValues ->
    Box(
        modifier = modifier
            .padding(paddingValues)
        ){
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 80.dp)
            ) {
                AsyncImage(
                    model = detailItem.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                        .height(400.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            )
                        )
                        .shadow(8.dp)
                )
                Column(
                    modifier = modifier
                        .padding(
                            horizontal = 16.dp
                        )
                ) {
                    Text(
                        text = detailItem.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier
                            .padding(top = 16.dp)
                    )
                    Text(
                        text = detailItem.japan_title,
                        modifier = modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = detailItem.year.toString(),
                        modifier = modifier.padding(top = 8.dp)
                    )

                    val genre = mutableListOf<String>()
                    detailItem.genre.forEach {
                        genre.add(it.name)
                    }

                    Row(modifier.padding(top = 16.dp)) {
                        Text(
                            text = stringResource(R.string.genre),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = genre.joinToString(", ")
                        )
                    }

                    Text(
                        modifier = modifier.padding(top = 16.dp),
                        text = detailItem.synopsis
                    )
                }
            }
            Box(
                modifier = modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f)
                            )
                        )
                    )
                    .align(Alignment.BottomCenter)
            ){
                Button(
                    onClick = {
                        navigateToFavorite()
                        favoriteViewModel.setFavorite(favoriteItem, true)
                    },
                    enabled = !favorite,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = textFavButton,
                        modifier = modifier
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}