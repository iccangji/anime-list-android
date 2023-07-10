package com.example.submissionjetpackcompose.di

import android.content.Context
import com.example.submissionjetpackcompose.data.local.AnimeDatabase
import com.example.submissionjetpackcompose.data.remote.ApiConfig
import com.example.submissionjetpackcompose.data.repository.AnimeRepository

object Injection {
    fun provideRepository(context: Context): AnimeRepository {
        val apiService = ApiConfig().getApiService()
        val database = AnimeDatabase.getInstance(context)
        val dao = database.animeDao()
        return AnimeRepository.getInstance(apiService,dao)
    }
}