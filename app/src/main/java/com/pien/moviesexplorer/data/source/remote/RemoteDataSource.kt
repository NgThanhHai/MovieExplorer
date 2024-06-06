package com.pien.moviesexplorer.data.source.remote

import com.pien.moviesexplorer.data.api.MoviesServices
import com.pien.moviesexplorer.data.source.MovieEntityMapper
import com.pien.moviesexplorer.domain.models.Movie
import com.pien.moviesexplorer.domain.models.MoviePaging

class RemoteDataSource(private val apiService: MoviesServices, private val mapper: MovieEntityMapper) {
    suspend fun getTrendingMovies(page: Int): MoviePaging {
        return mapper.mapMoviePagingDTOToMoviePaging(apiService.getTrendingMovies(page))
    }
    suspend fun searchMovie(query: String, page: Int): MoviePaging {
        return mapper.mapMoviePagingDTOToMoviePaging(apiService.searchMovies(query, page))
    }
    suspend fun getMovieDetail(movieId: Int): Movie {
        return mapper.mapMovieDetailToMovie(apiService.getMovieDetail(movieId) )
    }
}