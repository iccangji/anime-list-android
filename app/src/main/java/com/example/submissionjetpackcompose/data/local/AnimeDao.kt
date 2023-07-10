package com.example.submissionjetpackcompose.data.local

import androidx.room.*
import com.example.submissionjetpackcompose.data.entity.AnimeEntity

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime ORDER BY title ASC")
    suspend fun getAnime(): List<AnimeEntity>
//
//    @Query("SELECT * FROM anime where favorite = 1")
//    fun getBookmarkedNews(): Flow<List<AnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnime(anime: List<AnimeEntity>)

    @Update
    suspend fun updateAnime(item: AnimeEntity)

    @Query("DELETE FROM anime WHERE favorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM anime WHERE id = :id AND favorite = 1)")
    suspend fun isFavorite(id: Int): Boolean
}