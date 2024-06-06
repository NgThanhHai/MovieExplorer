package com.pien.moviesexplorer.data.source

import com.pien.moviesexplorer.data.models.MovieDTO
import com.pien.moviesexplorer.data.models.MovieDetail
import com.pien.moviesexplorer.data.models.MoviePagingDTO
import com.pien.moviesexplorer.data.source.local.entity.MovieEntity
import com.pien.moviesexplorer.domain.models.Movie
import com.pien.moviesexplorer.domain.models.MoviePaging

class MovieEntityMapper {

    fun mapMovieEntity(movieEntity: MovieEntity): Movie {
        return Movie(
            adult = movieEntity.adult,
            backdropPath = movieEntity.backdropPath,
            id = movieEntity.movieId,
            originalTitle = movieEntity.originalTitle,
            overview = movieEntity.overview,
            posterPath = movieEntity.posterPath,
            mediaType = movieEntity.mediaType,
            title = movieEntity.title.toString(),
            originalLanguage = movieEntity.originalLanguage,
            genresName = movieEntity.genreNames,
            popularity = movieEntity.popularity,
            releaseDate = movieEntity.releaseDate,
            video = movieEntity.video,
            voteCount = movieEntity.voteCount,
            voteAverage = movieEntity.voteAverage,
            dataDetailLoaded = movieEntity.isLoadedDetail,
            runTime = movieEntity.runTime,
            homepage = movieEntity.homepage
        )
    }

    fun mapMovieDTO(movieDTO: MovieDTO): Movie {
        return Movie(
            adult = movieDTO.adult,
            backdropPath = movieDTO.backdropPath,
            id = movieDTO.id ?: 0,
            originalTitle = movieDTO.originalTitle,
            overview = movieDTO.overview,
            posterPath = movieDTO.posterPath,
            mediaType = movieDTO.mediaType,
            title = movieDTO.title.toString(),
            originalLanguage = movieDTO.originalLanguage,
            genresName = listOf(),
            popularity = movieDTO.popularity,
            releaseDate = movieDTO.releaseDate,
            video = movieDTO.video,
            voteCount = movieDTO.voteCount,
            voteAverage = movieDTO.voteAverage,
            runTime = 0,
            homepage = ""
        )
    }

    fun mapMovieToEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            adult = movie.adult ?: false,
            backdropPath = movie.backdropPath ?: "",
            movieId = movie.id,
            originalTitle = movie.originalTitle ?: "",
            overview = movie.overview ?: "",
            posterPath = movie.posterPath ?: "",
            mediaType = movie.mediaType ?: "",
            title = movie.title,
            originalLanguage = movie.originalLanguage ?: "",
            genreNames = movie.genresName,
            popularity = movie.popularity ?: 0.00,
            releaseDate = movie.releaseDate ?: "",
            video = movie.video ?: false,
            voteCount = movie.voteCount ?: 0,
            voteAverage = movie.voteAverage ?: 0.00,
            isLoadedDetail = movie.dataDetailLoaded
        )
    }

    fun mapMoviePagingDTOToMoviePaging(moviePagingDTO: MoviePagingDTO): MoviePaging {
        return MoviePaging(
            page = moviePagingDTO.page,
            results = moviePagingDTO.results.map { mapMovieDTO(it) },
            totalPages = moviePagingDTO.totalPages,
            totalResults = moviePagingDTO.totalResults
        )
    }

    fun mapMovieDetailToMovie(movieDetail: MovieDetail): Movie {
        return Movie(
            adult = movieDetail.adult,
            backdropPath = movieDetail.backdropPath,
            id = movieDetail.id ?: 0,
            originalTitle = movieDetail.originalTitle,
            overview = movieDetail.overview,
            posterPath = movieDetail.posterPath,
            mediaType = "",
            title = movieDetail.title.toString(),
            originalLanguage = movieDetail.originalLanguage,
            genresName = movieDetail.genres.map { it.name.toString() },
            popularity = movieDetail.popularity,
            releaseDate = movieDetail.releaseDate,
            video = movieDetail.video,
            voteCount = movieDetail.voteCount,
            voteAverage = movieDetail.voteAverage,
            runTime = movieDetail.runtime,
            homepage = movieDetail.homepage,
            dataDetailLoaded = true
        )
    }
}