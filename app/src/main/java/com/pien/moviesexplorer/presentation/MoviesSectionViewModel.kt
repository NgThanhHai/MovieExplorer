package com.pien.moviesexplorer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pien.moviesexplorer.data.MovieRepository
import com.pien.moviesexplorer.data.reponse.ApiResponse
import com.pien.moviesexplorer.domain.models.Movie
import com.pien.moviesexplorer.utils.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class MoviesSectionViewModel(val repository: MovieRepository): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        getTrendingMovies(false)
    }

    fun getTrendingMovies(useCache: Boolean) {
        repository.getTrendingMovie(useCache).catch {
            it.printStackTrace()
        }.onEach { res ->
            when(res) {
                is ApiResponse.Loading -> {
                    _uiState.value = _uiState.value.copy(showLoading = true)
                }
                is ApiResponse.Error -> {
                    _uiState.value = _uiState.value.copy(showLoading = false, errorToast = res.throwable.message.toString())
                }
                is ApiResponse.Success<*> -> {
                    _uiState.value = _uiState.value.copy(showLoading = false, listMovies = res.data as List<Movie>, errorToast = "")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun searchByText(query: String) {
        _uiState.value = _uiState.value.copy(searchText = query)
        if(query.isNotEmpty()) {
            repository.searchMovie(query).catch {it.printStackTrace()
                it.printStackTrace()
                _uiState.value = _uiState.value.copy(showLoading = false, errorToast = "Something went wrong")
            }.onEach { res ->
                when(res) {
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(showLoading = true)
                    }
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(showLoading = false, errorToast = res.throwable.message.toString())
                    }
                    is ApiResponse.Success<*> -> {
                        _uiState.value = _uiState.value.copy(showLoading = false, listMovies = res.data as List<Movie>, errorToast = "")
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            getTrendingMovies(false)
        }
    }

    fun onClickMovie(movie: Movie) {
        repository.getMovieDetail(movie.id).onStart {
            _uiState.value = _uiState.value.copy(shouldShowHomePage = false, selectedMovie = null)
        }.catch { throwable -> throwable.printStackTrace()
            throwable.printStackTrace()
        }.onEach { res ->
            when(res) {
                is ApiResponse.Loading -> {
                    _uiState.value = _uiState.value.copy(showLoading = true)
                }
                is ApiResponse.Error -> {
                    _uiState.value = _uiState.value.copy(showLoading = false, errorToast = res.throwable.message.toString())
                }
                is ApiResponse.Success<*> -> {
                    _uiState.value = _uiState.value.copy(showLoading = false, selectedMovie = res.data as Movie, errorToast = "")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearSelectedMovie() {
        _uiState.value = _uiState.value.copy(shouldShowHomePage = true)
    }
}

data class UiState(
    var shouldShowHomePage: Boolean = true,
    var listMovies: List<Movie> = listOf(),
    var showLoading: Boolean = false,
    var errorToast: String = "",
    var searchText: String = "",
    var selectedMovie: Movie? = null
)