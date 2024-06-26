package com.kotlin.batuhan.movieappjetcompose.movieList.data.repository

import com.kotlin.batuhan.movieappjetcompose.R
import com.kotlin.batuhan.movieappjetcompose.movieList.data.local.movie.MovieDatabase
import com.kotlin.batuhan.movieappjetcompose.movieList.data.local.movie.MovieEntity
import com.kotlin.batuhan.movieappjetcompose.movieList.data.remote.MovieApi
import com.kotlin.batuhan.movieappjetcompose.movieList.data.mappers.toMovie
import com.kotlin.batuhan.movieappjetcompose.movieList.data.mappers.toMovieEntity
import com.kotlin.batuhan.movieappjetcompose.movieList.domain.model.Movie
import com.kotlin.batuhan.movieappjetcompose.movieList.domain.repository.MovieListRepository
import com.kotlin.batuhan.movieappjetcompose.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {

    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMovieList = movieDatabase.movieDao.getMovieListByCategory(category)

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMoviesList(category , page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))

        }
    }


    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {

            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id)

            if (movieEntity != null) {
                emit(
                    Resource.Success(movieEntity.toMovie(movieEntity.category))
                )

                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error no such movie"))

            emit(Resource.Loading(false))


        }
    }
    override suspend fun addToFavorites(movie: Movie) {
        val movieEntity = MovieEntity(
            adult = movie.adult,
            backdrop_path = movie.backdrop_path,
            genre_ids = movie.genre_ids.joinToString(","),
            original_language = movie.original_language,
            original_title = movie.original_title,
            overview = movie.overview,
            popularity = movie.popularity,
            poster_path = movie.poster_path,
            release_date = movie.release_date,
            title = movie.title,
            video = movie.video,
            vote_average = movie.vote_average,
            vote_count = movie.vote_count,
            id = movie.id,
            category = "favorites"
        )
        movieDatabase.movieDao.upsertMovieList(listOf(movieEntity))
    }

}