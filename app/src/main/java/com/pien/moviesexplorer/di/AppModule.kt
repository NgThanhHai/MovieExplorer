package com.pien.moviesexplorer.di

import android.content.Context
import androidx.room.Room
import com.pien.moviesexplorer.BuildConfig
import com.pien.moviesexplorer.data.MovieRepository
import com.pien.moviesexplorer.data.api.ApiInterceptor
import com.pien.moviesexplorer.data.api.MoviesServices
import com.pien.moviesexplorer.data.source.MovieEntityMapper
import com.pien.moviesexplorer.data.source.local.LocalDataSource
import com.pien.moviesexplorer.data.source.local.dao.MovieDAO
import com.pien.moviesexplorer.data.source.local.database.MovieExplorerDatabase
import com.pien.moviesexplorer.data.source.remote.RemoteDataSource
import com.pien.moviesexplorer.presentation.MoviesSectionViewModel
import com.pien.moviesexplorer.utils.NetworkUtils
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideService(get()) }
    single { provideDatabase(androidContext()) }
    single { provideMovieDao(get()) }
    single { MovieEntityMapper() }
    single { NetworkUtils.isNetworkAvailable(androidContext()) }
    factory {  RemoteDataSource(get(), get()) }
    factory {  LocalDataSource(get(), get()) }
    factory<MovieRepository> {  MovieRepository(get(), get(), androidContext()) }
    viewModel { MoviesSectionViewModel(repository = get()) }
}

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(ApiInterceptor(BuildConfig.API_KEY))
        .build()
}

fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()
fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .baseUrl(BuildConfig.BASE_URL)
        .build()
}

fun provideService(retrofit: Retrofit): MoviesServices =
    retrofit.create(MoviesServices::class.java)

fun provideDatabase(context: Context): MovieExplorerDatabase {
    return Room.databaseBuilder(context, MovieExplorerDatabase::class.java, "MovieExplorerDatabase").build()
}

fun provideMovieDao(movieExplorerDatabase: MovieExplorerDatabase): MovieDAO = movieExplorerDatabase.dao

