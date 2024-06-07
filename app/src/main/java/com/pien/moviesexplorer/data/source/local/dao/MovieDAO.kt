package com.pien.moviesexplorer.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pien.moviesexplorer.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Update
    suspend fun update(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE id = :id ORDER BY createdAt ASC")
    fun getMovieById(id: Int): Flow<MovieEntity>

}