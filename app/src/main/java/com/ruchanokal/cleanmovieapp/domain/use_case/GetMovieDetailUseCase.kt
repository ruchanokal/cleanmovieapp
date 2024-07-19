package com.ruchanokal.cleanmovieapp.domain.use_case

import com.ruchanokal.cleanmovieapp.data.remote.dto.toMovieDetail
import com.ruchanokal.cleanmovieapp.domain.model.MovieDetail
import com.ruchanokal.cleanmovieapp.domain.repository.MovieRepository
import com.ruchanokal.cleanmovieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    fun executeGetMovieDetail(imdbId: String): Flow<Resource<MovieDetail>> = flow  {

        try {
            emit(Resource.Loading())
            val movieDetail = movieRepository.getMovieDetail(imdbId).toMovieDetail()
            emit(Resource.Success(movieDetail))
        } catch (e : HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error!"))
        } catch (e : IOError) {
            emit(Resource.Error(e.localizedMessage ?: "IO Error"))
        }

    }
}