package com.pien.moviesexplorer.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.inject

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: MoviesSectionViewModel by inject()
    val gridState = rememberLazyGridState()
    val uiState = viewModel.uiState.collectAsState().value
    AnimatedVisibility(visible = uiState.shouldShowHomePage,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500)) +
                slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)) +
            scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 500)) +
            slideOutVertically(targetOffsetY = { it / 2 }, animationSpec = tween(durationMillis = 500))) {
        TrendingMoviesScreen(
            uiState,
            gridState,
            viewModel::searchByText,
            loadingMore = {
                if(uiState.searchText.isEmpty()) viewModel.getTrendingMovies(false)
            },
            onClickMovie = {
                viewModel.onClickMovie(movie = it)
            }
        )
    }
    AnimatedVisibility(visible = !uiState.shouldShowHomePage,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500)) +
                slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)) +
               scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 500)) +
               slideOutVertically(targetOffsetY = { it / 2 }, animationSpec = tween(durationMillis = 500))) {
        MovieDetailScreen(uiState, onBackPressed = viewModel::clearSelectedMovie)
    }
}