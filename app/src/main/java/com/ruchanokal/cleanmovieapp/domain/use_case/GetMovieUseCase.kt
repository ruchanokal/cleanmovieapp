package com.ruchanokal.cleanmovieapp.domain.use_case


import com.ruchanokal.cleanmovieapp.data.remote.dto.toMovieList
import com.ruchanokal.cleanmovieapp.domain.model.Movie
import com.ruchanokal.cleanmovieapp.domain.repository.MovieRepository
import com.ruchanokal.cleanmovieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(val movieRepository: MovieRepository) {

    fun executeGetMovies(searchString: String): Flow<Resource<List<Movie>>> = flow  {

        try {
            emit(Resource.Loading())
            val movieList = movieRepository.getMovies(searchString)
            if(movieList.Response.equals("True")){
                emit(Resource.Success(movieList.toMovieList()))
            } else {
                emit(Resource.Error("No movie found!"))
            }
        } catch (e : HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error!"))
        } catch (e : IOError) {
            emit(Resource.Error(e.localizedMessage ?: "IO Error"))
        }

    }

}