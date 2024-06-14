package com.kotlin.batuhan.movieappjetcompose.movieList.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size


@Composable
fun MoviePostdrop(
    imageUrl: String?,
    movieTitle: String?,
    modifier: Modifier = Modifier,
    imageHeight: Dp = 240.dp,
    imageWidth: Dp = 160.dp
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    ).state

    if (imageState is AsyncImagePainter.State.Error) {
        Box(
            modifier = modifier
                .size(imageWidth, imageHeight)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(70.dp),
                imageVector = Icons.Rounded.ImageNotSupported,
                contentDescription = movieTitle
            )
        }
    }

    if (imageState is AsyncImagePainter.State.Success) {
        Image(
            modifier = modifier
                .size(imageWidth, imageHeight)
                .clip(RoundedCornerShape(12.dp)),
            painter = imageState.painter,
            contentDescription = movieTitle,
            contentScale = ContentScale.Crop
        )
    }
}
