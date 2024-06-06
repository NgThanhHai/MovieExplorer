package com.pien.moviesexplorer.data.api

import com.pien.moviesexplorer.data.models.MovieDetail
import com.pien.moviesexplorer.data.models.MoviePagingDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesServices {
    @GET("trending/movie/day?language=en-US")
    suspend fun getTrendingMovies(@Query("page") page: Int): MoviePagingDTO

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String, @Query("page") page: Int, @Query("language") language: String = "en-US"): MoviePagingDTO

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int, @Query("language") language: String = "en-US"): MovieDetail
}