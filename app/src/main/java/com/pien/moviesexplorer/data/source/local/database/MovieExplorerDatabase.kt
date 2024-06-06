package com.pien.moviesexplorer.data.source.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.pien.moviesexplorer.data.source.local.dao.MovieDAO
import com.pien.moviesexplorer.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 2)
abstract class MovieExplorerDatabase: RoomDatabase() {
    abstract val dao: MovieDAO
}