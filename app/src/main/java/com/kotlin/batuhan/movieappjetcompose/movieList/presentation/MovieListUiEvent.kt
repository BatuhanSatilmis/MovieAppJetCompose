package com.kotlin.batuhan.movieappjetcompose.movieList.presentation

sealed interface MovieListUiEvent {
    data class Paginate(val category: String) : MovieListUiEvent
    data class ChangeLanguage(val language: String) : MovieListUiEvent
     object Navigate: MovieListUiEvent

}