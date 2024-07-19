package com.ruchanokal.cleanmovieapp.presentation.movie_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruchanokal.cleanmovieapp.domain.model.MovieDetail
import com.ruchanokal.cleanmovieapp.domain.use_case.GetMovieDetailUseCase
import com.ruchanokal.cleanmovieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val getMovieDetailUseCase: GetMovieDetailUseCase) : ViewModel() {

    private val _movie = MutableStateFlow(
        MovieDetail(
            Actors = "N/A",
            Awards = "N/A",
            Country = "N/A",
            Director = "N/A",
            Genre = "N/A",
            Language = "N/A",
            Poster = "N/A",
            Rated = "N/A",
            Released = "N/A",
            Title = "N/A",
            Type = "N/A",
            Year = "N/A",
            imdbRating = "N/A"
        )
    )
    val movie = _movie.asStateFlow()

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String>("")
    val error = _error.asStateFlow()

    fun getMovieDetail(imdbId: String) {

        _error.value = ""

        getMovieDetailUseCase.executeGetMovieDetail(imdbId).onEach {
            when (it) {
                is Resource.Success -> {

                    _loading.value = false
                    it.data?.let {
                        _movie.value = it
                    }
                }

                is Resource.Error -> {

                    _loading.value = false
                    it.message?.let {
                        _error.value = it
                    }

                }

                is Resource.Loading -> {
                    _loading.value = true
                }
            }
        }.launchIn(viewModelScope)
    }



}