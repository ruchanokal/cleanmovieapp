package com.ruchanokal.cleanmovieapp.data.remote.api

import com.ruchanokal.cleanmovieapp.data.remote.dto.MovieDetailDto
import com.ruchanokal.cleanmovieapp.data.remote.dto.MoviesDto
import com.ruchanokal.cleanmovieapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    //http://www.omdbapi.com/?i=tt3896198&apikey=afc324f1  // imdb id ile tararken
    //http://www.omdbapi.com/?s=batman&apikey=afc324f1 // search ederken

    @GET(".")
    suspend fun getMovies(
        @Query("s") searchString : String,
        @Query("apikey") apiKey : String = Constants.API_KEY
    ) : MoviesDto

    @GET(".")
    suspend fun getMovieDetail(
        @Query("i") imdbId : String,
        @Query("apikey") apiKey : String = Constants.API_KEY
    ) : MovieDetailDto


}