package com.kotlin.batuhan.movieappjetcompose.core.details.presentation

import com.kotlin.batuhan.movieappjetcompose.movieList.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie : Movie? = null
)