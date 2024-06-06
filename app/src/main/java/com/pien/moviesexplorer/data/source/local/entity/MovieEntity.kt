package com.pien.moviesexplorer.data.source.local.entity

import java.util.Calendar
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = tableName)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int = 0,
    var backdropPath : String? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var posterPath: String? = null,
    var mediaType: String? = null,
    var adult: Boolean? = null,
    var title: String? = null,
    var originalLanguage : String? = null,
    @field:TypeConverters(StringTypeConverter::class)
    var genreNames: List<String> = emptyList(),
    var popularity: Double? = null,
    var releaseDate: String? = null,
    var video: Boolean? = null,
    var voteAverage: Double? = null,
    var voteCount: Int? = null,
    var runTime: Int? = null,
    var homepage: String? = null,
    @field:TypeConverters(DateTypeConverter::class)
    val createdAt: Date = Calendar.getInstance().time,
    val isLoadedDetail: Boolean = false
)
const val tableName = "MovieEntity"