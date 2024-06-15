package com.pien.moviesexplorer.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: MoviesSectionViewModel) {
    val gridState = rememberLazyGridState()
    val uiState = viewModel.uiState.collectAsState().value
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }
    SharedTransitionLayout(modifier = modifier) {
        NavHost(navController = navController, startDestination = Routes.TRENDING_SCREEN) {
            composable(Routes.TRENDING_SCREEN) {
                TrendingMoviesScreen(
                    uiState,
                    gridState,
                    animatedVisibilityScope = this,
                    viewModel::searchByText,
                    loadingMore = {
                        if(uiState.searchText.isEmpty()) viewModel.getTrendingMovies(false)
                    },
                    onClickMovie = {
                        actions.openMovieDetailScreen(it.posterPath.toString())
                        viewModel.onClickMovie(movie = it)
                    }
                )
            }
            composable(Routes.MOVIE_DETAIL_SCREEN,
                arguments = listOf(navArgument(MovieDetailScreenArgs.MOVIE_POSTER_PATH) { type = NavType.Companion.StringType})) {
                MovieDetailScreen(uiState, it.arguments?.getString(MovieDetailScreenArgs.MOVIE_POSTER_PATH).toString(), this, onBackPressed = {
                    actions.navigateBack()
                })
            }
        }
    }
}
object Routes {
    const val TRENDING_SCREEN = "trendingScreen"
    const val MOVIE_DETAIL_SCREEN= "movieDetailScreen/{${MovieDetailScreenArgs.MOVIE_POSTER_PATH}}"
    fun createRoute(url: String) =
        MOVIE_DETAIL_SCREEN.replace(
            MovieDetailScreenArgs.toPath(MovieDetailScreenArgs.MOVIE_POSTER_PATH), url.replace("/", ""))

}
object MovieDetailScreenArgs {
    const val MOVIE_POSTER_PATH = "MOVIE_POSTER_PATH"
    fun toPath(param: String) = "{${param}}"
}
class Actions(navController: NavHostController) {
    val openMovieDetailScreen: (String) -> Unit = { url ->
        navController.navigate(Routes.createRoute(url))
    }
    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }
}