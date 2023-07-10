package com.example.submissionjetpackcompose.data.remote.entity

import com.google.gson.annotations.SerializedName

data class DetailAnimeResponse(

	@field:SerializedName("data")
	val data: Data?
)

data class Data(

	@field:SerializedName("title_japanese")
	val titleJapanese: String,

	@field:SerializedName("favorites")
	val favorites: Int,

	@field:SerializedName("broadcast")
	val broadcast: Broadcast,

	@field:SerializedName("year")
	val year: Int,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("scored_by")
	val scoredBy: Int,

	@field:SerializedName("title_synonyms")
	val titleSynonyms: List<String>,

	@field:SerializedName("source")
	val source: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("trailer")
	val trailer: Trailer,

	@field:SerializedName("duration")
	val duration: String,

	@field:SerializedName("score")
	val score: Any,

	@field:SerializedName("themes")
	val themes: List<ThemesItem>,

	@field:SerializedName("approved")
	val approved: Boolean,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("popularity")
	val popularity: Int,

	@field:SerializedName("members")
	val members: Int,

	@field:SerializedName("title_english")
	val titleEnglish: String,

	@field:SerializedName("rank")
	val rank: Int,

	@field:SerializedName("season")
	val season: String,

	@field:SerializedName("airing")
	val airing: Boolean,

	@field:SerializedName("episodes")
	val episodes: Int,

	@field:SerializedName("aired")
	val aired: Aired,

	@field:SerializedName("images")
	val images: Images,

	@field:SerializedName("studios")
	val studios: List<StudiosItem>,

	@field:SerializedName("mal_id")
	val malId: Int,

	@field:SerializedName("titles")
	val titles: List<TitlesItem>,

	@field:SerializedName("synopsis")
	val synopsis: String,

	@field:SerializedName("explicit_genres")
	val explicitGenres: List<Any>,

	@field:SerializedName("licensors")
	val licensors: List<LicensorsItem>,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("producers")
	val producers: List<ProducersItem>,

	@field:SerializedName("background")
	val background: Any,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("demographics")
	val demographics: List<DemographicsItem>
)
