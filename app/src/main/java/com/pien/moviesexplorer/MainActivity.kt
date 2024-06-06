package com.pien.moviesexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.pien.moviesexplorer.presentation.HomeScreen
import com.pien.moviesexplorer.ui.theme.MoviesExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesExplorerTheme {
                HomeScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}