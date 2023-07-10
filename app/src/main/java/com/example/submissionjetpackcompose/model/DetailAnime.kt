package com.example.submissionjetpackcompose.model

import com.example.submissionjetpackcompose.data.remote.entity.GenresItem

data class DetailAnime(
    val id: Int,
    val title: String,
    val japan_title: String,
    val image: String,
    val synopsis: String,
    val year: Int,
    val genre: List<GenresItem>,
)
