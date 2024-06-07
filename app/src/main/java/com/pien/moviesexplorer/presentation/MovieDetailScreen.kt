package com.pien.moviesexplorer.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.pien.moviesexplorer.R
import com.pien.moviesexplorer.common.HyperlinkText
import com.pien.moviesexplorer.common.ImagePosterView
import com.pien.moviesexplorer.common.NoConnectionScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(uiState: UiState, onBackPressed: () -> Unit) {
    val navController = rememberNavController()
    val movie = uiState.selectedMovie
    BackHandler {
        onBackPressed()
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    movie?.let {
                        Text(it.title)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.clickable {
                                onBackPressed()
                            }
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            if (uiState.showLoading) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    IndeterminateCircularIndicator(
                        Modifier
                            .height(48.dp)
                            .width(48.dp)
                            .padding(8.dp)
                    )
                }
            } else if (uiState.errorToast.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                NoConnectionScreen(modifier = Modifier.padding(30.dp), uiState.errorToast)
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                movie?.let {
                    it.posterPath?.let { urlPath ->
                        ImagePosterView(
                            modifier = Modifier
                                .height(400.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)), urlPath
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Release Date: ${it.releaseDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (movie.genresName.isNotEmpty()) {
                        Row(modifier = Modifier) {
                            Text(
                                text = "Genres: ",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            movie.genresName.forEachIndexed { index, genre ->
                                val textToShow = if (index == movie.genresName.lastIndex) {
                                    genre
                                } else "$genre, "
                                Text(
                                    text = textToShow,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    val ratingString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        ) {
                            append("Rating: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        ) {
                            append("${it.voteAverage}/10 ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        ) {
                            append("by ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        ) {
                            append("${it.voteCount} viewers")
                        }
                    }
                    Text(
                        text = ratingString,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val runningTimeString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        ) {
                            append("Running time: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        ) {
                            append("${it.runTime} minutes")
                        }
                    }
                    Text(
                        text = runningTimeString,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    val ratedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        ) {
                            append("Content: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        ) {
                            append(
                                if (it.adult == false) {
                                    "For all ages"
                                } else {
                                    "Adults only"
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = ratedString,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Overview: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it.overview.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    it.homepage?.let { homepageUrl ->
                        val showText = "For more information: $homepageUrl"
                        HyperlinkText(fullText = showText, linkText = listOf(homepageUrl), hyperlinks = listOf(homepageUrl), fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}



