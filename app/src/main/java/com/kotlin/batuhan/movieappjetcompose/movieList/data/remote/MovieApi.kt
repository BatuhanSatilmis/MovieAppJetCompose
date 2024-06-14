package com.kotlin.batuhan.movieappjetcompose.movieList.data.remote

import com.kotlin.batuhan.movieappjetcompose.movieList.data.remote.respond.MovieListDto
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("language") language: String = TURKISH,
        @Query("api_key") apiKey : String = API_KEY,

        ) : MovieListDto



    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "02b04e37221f531c34ad64237f17c2d5"
        const val TURKISH = "tr-TR"
        const val ENGLISH = "en-US"
    }
}