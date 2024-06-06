package com.pien.moviesexplorer.data

import android.content.Context
import com.pien.moviesexplorer.data.models.MovieDTO
import com.pien.moviesexplorer.data.reponse.ApiResponse
import com.pien.moviesexplorer.data.source.local.LocalDataSource
import com.pien.moviesexplorer.data.source.remote.RemoteDataSource
import com.pien.moviesexplorer.domain.models.Movie
import com.pien.moviesexplorer.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class MovieRepository(private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appContext: Context
) {

    private val movies: MutableList<Movie> = mutableListOf()
    private var currentPage: Int? = 0

    fun getTrendingMovie(useOffline: Boolean = false): Flow<ApiResponse<List<Movie>>> {
        return if (!useOffline && NetworkUtils.isNetworkAvailable(appContext)) {
            getRemoteTrendingMovie()
        } else {
            flow {
                emit(ApiResponse.Loading)
                val localMovies = localDataSource.getSavedMovies().firstOrNull()
                if(localMovies.isNullOrEmpty()) {
                    getRemoteTrendingMovie().onEach { emit(it) }.collect()
                } else {
                    emit(ApiResponse.Success(localMovies))
                }
            }
        }
    }

    private fun getRemoteTrendingMovie(): Flow<ApiResponse<List<Movie>>> {
        return if(!NetworkUtils.isNetworkAvailable(appContext)) {
            flow { emit(ApiResponse.Error(Throwable("No network is available"))) }
        } else flow {
            remoteDataSource.getTrendingMovies(currentPage?.plus(1) ?: 1).apply {
                currentPage = page
                results.forEach {
                    if(!movies.contains(it)) movies.add(it)
                    localDataSource.saveMovies(it)
                }
                emit(ApiResponse.Success(movies))
            }
        }
    }

    fun searchMovie(query: String) = flow {
        emit(ApiResponse.Loading)
        val result = remoteDataSource.searchMovie(query, currentPage?.plus(1) ?: 1)
        emit(ApiResponse.Success(result.results))
    }

    fun getMovieDetail(movieId: Int) = flow {
        emit(ApiResponse.Loading)
        var movieDetail = localDataSource.getMovieById(movieId).firstOrNull()
        if (movieDetail == null || !movieDetail.dataDetailLoaded) {
            if(NetworkUtils.isNetworkAvailable(appContext)) {
                movieDetail = remoteDataSource.getMovieDetail(movieId)
                localDataSource.saveMovies(movieDetail)
                emit(ApiResponse.Success(movieDetail))
            } else {
                emit(ApiResponse.Error(Throwable("No network is available")))
            }
        } else {
            emit(ApiResponse.Success(movieDetail))
        }
    }

}