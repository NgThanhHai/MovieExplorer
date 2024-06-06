package com.pien.moviesexplorer.domain.models

data class MoviePaging(
    var page: Int? = null,
    var results: List<Movie> = listOf(),
    var totalPages: Int? = null,
    var totalResults: Int? = null
)