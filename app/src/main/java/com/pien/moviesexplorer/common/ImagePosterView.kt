package com.pien.moviesexplorer.common

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.pien.moviesexplorer.BuildConfig

@Composable
fun ImagePosterView(modifier: Modifier = Modifier, urlPath: String){
    var image by remember { mutableStateOf<Drawable?>(null) }
    Glide.with(LocalContext.current).load(BuildConfig.BASE_URL_IMAGE + urlPath).into(object : CustomTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            image = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {
        }
    })
    image?.let {
        Image(
            painter = rememberDrawablePainter(it),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}