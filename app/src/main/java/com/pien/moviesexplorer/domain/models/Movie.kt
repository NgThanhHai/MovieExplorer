package com.pien.moviesexplorer.domain.models

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String?,
    val mediaType: String?,
    val adult: Boolean?,
    val backdropPath: String?,
    val genresName: List<String>,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val originalLanguage: String?,
    var video: Boolean?,
    val voteAverage: Double?,
    var voteCount: Int?,
    var runTime: Int?,
    var homepage: String?,
    var dataDetailLoaded: Boolean = false,
)