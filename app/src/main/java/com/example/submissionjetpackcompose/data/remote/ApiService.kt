package com.example.submissionjetpackcompose.data.remote

import com.example.submissionjetpackcompose.data.remote.entity.AnimeItemResponse
import com.example.submissionjetpackcompose.data.remote.entity.DetailAnimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("top/anime?")
    suspend fun getAll(
        @Query("type") type: String
    ): AnimeItemResponse

    @GET("anime/{id}")
    suspend fun getDetail(
        @Path("id") id: Int
    ): DetailAnimeResponse
}