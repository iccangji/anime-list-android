package com.example.submissionjetpackcompose.data.repository

import android.util.Log
import com.example.submissionjetpackcompose.data.entity.AnimeEntity
import com.example.submissionjetpackcompose.data.local.AnimeDao
import com.example.submissionjetpackcompose.data.remote.ApiService
import com.example.submissionjetpackcompose.model.DetailAnime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AnimeRepository private constructor(
    private val apiService: ApiService,
    private val animeDao: AnimeDao
) {

    suspend fun getAnimeList(): Flow<List<AnimeEntity>>  {
        try {
            val response = apiService.getAll("tv")
            val animeList: MutableList<AnimeEntity> = mutableListOf()
            response.data.forEach {
                val isBookmarked = animeDao.isFavorite(it.malId)
                animeList.add(
                    AnimeEntity(
                        it.malId,
                        it.title,
                        it.images.jpg.imageUrl,
                        it.year,
                        isBookmarked
                    )
                )
            }
            animeDao.deleteAll()
            animeDao.insertAnime(animeList)

        } catch (e: Exception) {
            Log.e(
                "ERR",
                "Error: ${e.message}"
            )
        }

        return flowOf(animeDao.getAnime())
    }

    suspend fun searchAnime(query: String) : Flow<List<AnimeEntity>>{
        val animeList = animeDao.getAnime()
        return if(query != "") {
            flowOf(animeList.filter {
                it.title.contains(
                    query,
                    ignoreCase = true
                )
            })
        }else{
            flowOf(animeList)
        }
    }

    suspend fun searchFavorite(query: String) : Flow<List<AnimeEntity>>{
        val animeList = animeDao.getAnime().filter { it.favorite }
        return if(query != "") {
            flowOf(animeList.filter {
                it.title.contains(
                    query,
                    ignoreCase = true
                )
            })
        }else{
            flowOf(animeList)
        }
    }

    suspend fun getAnimeDetail(id: Int): Flow<DetailAnime?> {
        var animeDetail: DetailAnime? = null
        try {
            val response = apiService.getDetail(id)
            if (response.data != null) {
                response.data.let{
                    animeDetail = DetailAnime(
                        id = it.malId,
                        title = it.title,
                        japan_title = it.titleJapanese,
                        image = it.images.jpg.largeImageUrl,
                        synopsis = it.synopsis,
                        genre = it.genres,
                        year = it.year,
                    )
                }
            } else {
                Log.e("ERR","Failed to fetch items")
            }
        } catch (e: Exception) {
            Log.e("ERR","Error: ${e.message}")
        }
        return flowOf(animeDetail)
    }

    suspend fun setFavoriteItem(item: AnimeEntity, added: Boolean){
        item.favorite = added
        animeDao.updateAnime(item)
    }

    suspend fun getFavoriteList(): Flow<List<AnimeEntity>> {
        return flowOf(
            animeDao.getAnime().filter {
                it.favorite
            }
        )
    }

    companion object {
        @Volatile
        private var instance: AnimeRepository? = null
        fun getInstance(
            apiService: ApiService,
            animeDao: AnimeDao
        ): AnimeRepository =
            instance ?: synchronized(this) {
                instance ?: AnimeRepository(
                    apiService,
                    animeDao
                )
            }.also { instance = it }
    }
}
