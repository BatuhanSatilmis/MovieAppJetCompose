package com.kotlin.batuhan.movieappjetcompose.movieList.util

sealed class Screen(val rout: String){
 object Home : Screen("main")
 object PopularMovieList : Screen("popularMovie")
 object UpcomingMovieList : Screen("upcomingMovie")
 object Details : Screen("details")
 object Favorites : Screen ("Favorites")


}