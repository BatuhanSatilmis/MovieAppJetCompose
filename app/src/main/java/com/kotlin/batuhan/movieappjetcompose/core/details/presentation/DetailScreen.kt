package com.kotlin.batuhan.movieappjetcompose.core.details.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.kotlin.batuhan.movieappjetcompose.movieList.data.remote.MovieApi
import com.kotlin.batuhan.movieappjetcompose.movieList.presentation.MovieListState
import com.kotlin.batuhan.movieappjetcompose.movieList.presentation.MovieListUiEvent
import com.kotlin.batuhan.movieappjetcompose.movieList.presentation.components.MovieBackdrop
import com.kotlin.batuhan.movieappjetcompose.movieList.util.getAverageColor

@Composable
fun DetailScreen(
    backStackEntry: NavBackStackEntry
) {
    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailsState = detailsViewModel.detailsState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MovieBackdrop(
            imageUrl = MovieApi.IMAGE_BASE_URL + detailsState.movie?.backdrop_path,
            movieTitle = detailsState.movie?.title
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                MovieBackdrop(
                    imageUrl = MovieApi.IMAGE_BASE_URL + detailsState.movie?.poster_path,
                    movieTitle = detailsState.movie?.title,
                    modifier = Modifier.clip(RoundedCornerShape(16.dp),
                    )

                )
            }
        }
    }
}