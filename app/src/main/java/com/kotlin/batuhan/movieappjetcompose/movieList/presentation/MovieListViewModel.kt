package com.kotlin.batuhan.movieappjetcompose.movieList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.batuhan.movieappjetcompose.movieList.domain.repository.MovieListRepository
import com.kotlin.batuhan.movieappjetcompose.movieList.util.Category
import com.kotlin.batuhan.movieappjetcompose.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
             viewModelScope.launch {
                 _movieListState.update {
                     it.copy(isLoading = true)
                 }
                 movieListRepository.getMovieList(
                     forceFetchFromRemote,
                     Category.POPULAR,
                     movieListState.value.popularMovieListPage
                 ).collectLatest { result ->
                     when(result){
                         is Resource.Error -> {
                             _movieListState.update {
                                 it.copy(isLoading = false)
                             }
                         }
                         is Resource.Success -> {

                           result.data?.let { popularList ->
                               _movieListState.update {
                                   it.copy(
                       popularMovieList = movieListState.value.popularMovieList + popularList.shuffled()
                                   )
                               }

                           }

                         }
                         is Resource.Loading -> {
                             _movieListState.update {
                                 it.copy(isLoading = result.isLoading )
                             }

                         }
                     }
                 }
             }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {

    }
}