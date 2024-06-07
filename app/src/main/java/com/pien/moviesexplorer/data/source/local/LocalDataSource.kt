package com.pien.moviesexplorer.data.source.local

import android.text.format.DateUtils
import com.pien.moviesexplorer.data.source.MovieEntityMapper
import com.pien.moviesexplorer.data.source.local.dao.MovieDAO
import com.pien.moviesexplorer.domain.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LocalDataSource(private val movieDao: MovieDAO, private val mapper: MovieEntityMapper) {

    fun getSavedMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().flowOn(Dispatchers.IO).map {
            it.filter {
                val isValid = DateUtils.isToday(it.createdAt.time)
                if(!isValid) movieDao.delete(it)
                isValid
            }.map {
                mapper.mapMovieEntity(it)
            }
        }
    }

    fun getMovieById(id: Int): Flow<Movie> {
        return movieDao.getMovieById(id).flowOn(Dispatchers.IO).map {
            mapper.mapMovieEntity(it)
        }
    }

    suspend fun saveMovies(movie: Movie) {
        movieDao.insert(mapper.mapMovieToEntity(movie))
    }
}