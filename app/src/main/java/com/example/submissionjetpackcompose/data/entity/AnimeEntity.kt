package com.example.submissionjetpackcompose.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
class AnimeEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "title")
    val title: String,

    @field:ColumnInfo(name = "image")
    val image: String,

    @field:ColumnInfo(name = "year")
    val year: Int,

    @field:ColumnInfo(name = "favorite")
    var favorite: Boolean
)