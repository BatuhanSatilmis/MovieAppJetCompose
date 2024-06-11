package com.kotlin.batuhan.movieappjetcompose.movieList.presentation

sealed interface MovieListUiEvent {
    data class Paginate(val category: String) : MovieListUiEvent
    data object Navigate: MovieListUiEvent

}