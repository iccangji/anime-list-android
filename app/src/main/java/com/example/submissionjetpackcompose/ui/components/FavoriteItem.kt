package com.example.submissionjetpackcompose.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.submissionjetpackcompose.data.entity.AnimeEntity
import com.example.submissionjetpackcompose.di.Injection
import com.example.submissionjetpackcompose.ui.view_model.FavoriteListViewModel
import com.example.submissionjetpackcompose.ui.view_model.ViewModelFactory
import com.example.submissionjetpackcompose.R

@Composable
fun FavoriteItemCard(
    modifier: Modifier = Modifier,
    navigateToDetail : (Int, Boolean) -> Unit,
    data : AnimeEntity,
    viewModel: FavoriteListViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
){
    Spacer(modifier = modifier.height(8.dp))
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable {
                navigateToDetail(data.id, data.favorite)
            },
        shape = RoundedCornerShape(12.dp)
    ){
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = data.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
            )
            Column(
                modifier = modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = data.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = data.year.toString(),
                    fontSize = 14.sp,
                    modifier = modifier
                        .padding(end=8.dp)
                )
            }
            IconButton(onClick = {
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.delete_favorite),
                    modifier = modifier
                        .padding(
                            end = 8.dp
                        )
                        .clickable {
                            viewModel.setFavorite(data, false)
                            viewModel.getFavoriteAnime()
                        }
                )
            }
        }
    }
}