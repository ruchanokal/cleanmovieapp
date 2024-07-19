package com.ruchanokal.cleanmovieapp.presentation.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruchanokal.cleanmovieapp.domain.model.Movie
import com.ruchanokal.cleanmovieapp.domain.use_case.GetMovieUseCase
import com.ruchanokal.cleanmovieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val getMovieUseCase: GetMovieUseCase) : ViewModel() {

    private val TAG = "MV_MoviewViewModel"

    private val _moviesList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList = _moviesList.asStateFlow()

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String>("")
    val error = _error.asStateFlow()

    private var job : Job? = null

    init {
        getMoviesList("batman")
    }

    fun getMoviesList(searchString : String) {

        Log.i(TAG,"getMoviesList: $searchString")
        job?.cancel()
        _error.value = ""
        _moviesList.value = arrayListOf()

        job = getMovieUseCase.executeGetMovies(searchString).onEach {

            when(it){
                is Resource.Loading ->{
                    Log.i(TAG,"Loading")
                    _loading.value = true
                }

                is Resource.Error ->{
                    Log.i(TAG,"Error")
                    _loading.value = false
                    _error.value = it.message ?: "Error"
                }

                is Resource.Success ->{
                    Log.i(TAG,"Success")
                    _loading.value = false
                    it.data?.let { list ->
                        Log.i(TAG,"Success: moviesList: $_moviesList, list: $list")
                        _moviesList.value = list
                    }

                }
            }
        }.launchIn(viewModelScope)

    }

}