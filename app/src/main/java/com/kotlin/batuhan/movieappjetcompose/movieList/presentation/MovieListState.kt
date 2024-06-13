package com.kotlin.batuhan.movieappjetcompose.movieList.presentation

import com.kotlin.batuhan.movieappjetcompose.movieList.domain.model.Movie
import com.kotlin.batuhan.movieappjetcompose.movieList.domain.repository.MovieListRepository

data class MovieListState (
    val isLoading: Boolean = false,
    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,
    val isCurrentPopularScreen: Boolean = true,
    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
    val favoriteMovieList: List<Movie> = emptyList()
)