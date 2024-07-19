package com.ruchanokal.cleanmovieapp.data.repository

import com.ruchanokal.cleanmovieapp.data.remote.api.MovieAPI
import com.ruchanokal.cleanmovieapp.data.remote.dto.MovieDetailDto
import com.ruchanokal.cleanmovieapp.data.remote.dto.MoviesDto
import com.ruchanokal.cleanmovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(val api : MovieAPI) : MovieRepository {

    override suspend fun getMovies(searchString: String): MoviesDto {
        return api.getMovies(searchString)
    }

    override suspend fun getMovieDetail(imdbId: String): MovieDetailDto {
        return api.getMovieDetail(imdbId)
    }

}