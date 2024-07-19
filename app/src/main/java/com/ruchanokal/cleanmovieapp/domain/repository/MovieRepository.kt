package com.ruchanokal.cleanmovieapp.domain.repository

import com.ruchanokal.cleanmovieapp.data.remote.dto.MovieDetailDto
import com.ruchanokal.cleanmovieapp.data.remote.dto.MoviesDto

interface MovieRepository {

    suspend fun getMovies(searchString : String) : MoviesDto

    suspend fun getMovieDetail(imdbId : String) : MovieDetailDto

}