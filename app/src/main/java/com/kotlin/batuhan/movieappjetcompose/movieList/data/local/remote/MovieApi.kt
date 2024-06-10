package com.kotlin.batuhan.movieappjetcompose.movieList.data.local.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMoviesList(
           @Path("category") category : String,
           @Query("page") page : Int,
           @Query("api_key") apiKey : String = API_KEY

    )

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "02b04e37221f531c34ad64237f17c2d5"
    }
}