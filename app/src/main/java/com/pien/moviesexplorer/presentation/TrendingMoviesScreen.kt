package com.pien.moviesexplorer.presentation

import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.pien.moviesexplorer.BuildConfig
import com.pien.moviesexplorer.domain.models.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingMoviesScreen(uiState: UiState, state: LazyGridState, onQueryTextChanged: (String) -> Unit, loadingMore: () -> Unit, onClickMovie: (Movie) -> Unit) {
    var searchActive by remember {
        mutableStateOf(false)
    }
    var focusManager = LocalFocusManager.current
    LaunchedEffect(state.isScrollInProgress && !state.canScrollForward) {
        if(uiState.listMovies.isNotEmpty()) {
            loadingMore()
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Trending Movies")
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            SearchBar(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 0.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(15.dp))
                .height(70.dp),
                query = uiState.searchText,
                placeholder = { Text("Search your movie...", modifier = Modifier.height(20.dp)) },
                onQueryChange = { onQueryTextChanged(it) },
                onSearch = { searchActive = false },
                onActiveChange = { searchActive = it },
                active = true,
                content = { },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                },
                trailingIcon = {
                    if(searchActive) {
                        Icon(Icons.Default.Clear,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                if (uiState.searchText.isNotEmpty()) {
                                    onQueryTextChanged("")
                                } else {
                                    focusManager.clearFocus()
                                    searchActive  = false
                                }
                            }
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if(uiState.showLoading) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    IndeterminateCircularIndicator(
                        Modifier
                            .height(48.dp)
                            .width(48.dp)
                            .padding(8.dp))
                }
            }
            LazyVerticalGrid(columns = GridCells.Adaptive(170.dp), state = state, modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(uiState.listMovies) { movie ->
                    MovieItem(movie, onClickMovie = {
                        onClickMovie(movie)
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            if(uiState.errorToast.isNotEmpty()) {
                Toast.makeText(LocalContext.current, uiState.errorToast, Toast.LENGTH_SHORT).show()
            }
        }
    }

}

@Composable
fun IndeterminateCircularIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}
@Composable
fun MovieItem(movie: Movie, onClickMovie: () -> Unit, modifier: Modifier = Modifier) {
    var image by remember { mutableStateOf<Drawable?>(null) }
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp), modifier = modifier
        .fillMaxWidth()
        .padding(8.dp),
        onClick = { onClickMovie() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF),
        )) {
        Glide.with(LocalContext.current).load(BuildConfig.BASE_URL_IMAGE + movie.posterPath).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                image = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
        image?.let {
            Image(
                painter = rememberDrawablePainter(it),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = movie.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp).padding(horizontal = 2.dp),
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Date: " + movie.releaseDate.toString(),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp).padding(horizontal = 2.dp)
        )
        Text(
            text = "Rating: " + movie.voteAverage.toString(),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp).padding(horizontal = 2.dp)
        )
    }
}

