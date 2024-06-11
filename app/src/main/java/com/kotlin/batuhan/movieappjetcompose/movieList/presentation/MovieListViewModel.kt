package com.kotlin.batuhan.movieappjetcompose.movieList.presentation

import androidx.lifecycle.ViewModel
import com.kotlin.batuhan.movieappjetcompose.movieList.domain.repository.MovieListRepository
import com.kotlin.batuhan.movieappjetcompose.movieList.util.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor( private val movieListRepository: MovieListRepository) :
    ViewModel() {
      private var _movieListState = MutableStateFlow(MovieListState())
      val movieListState = _movieListState.asStateFlow()
      init {
          getPopularMovieList(false)
          getUpcomingMovieList(false)
      }



    private fun onEvent(event: MovieListUiEvent) {
        when (event) {
            MovieListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen)
                }
            }
            is MovieListUiEvent.Paginate -> {
                when (event.category) {
                    Category.POPULAR -> getPopularMovieList(true)
                    Category.UPCOMING -> getUpcomingMovieList(true)
                }
            }
        }
    }
    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {

    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {

    }
}