package com.ruchanokal.cleanmovieapp.presentation.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruchanokal.cleanmovieapp.R
import com.ruchanokal.cleanmovieapp.databinding.FragmentMovieBinding
import com.ruchanokal.cleanmovieapp.domain.model.Movie
import com.ruchanokal.cleanmovieapp.presentation.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val TAG = "MV_MainFragment"
    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()
    private var movieAdapter : MovieAdapter? = null
    private var movieList = arrayListOf<Movie>()
    private var movieName = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        searchMovies()
        observeDatas()
        tryAgain()

    }

    private fun tryAgain() {

        binding.tryAgainImage.setOnClickListener {
            binding.errorTryAgainLayout.visibility = View.GONE
            viewModel.getMoviesList("batman")
        }

    }

    private fun observeDatas() {

        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.movieList.collectLatest { list ->
                    Log.i(TAG, "movieList collected: $list")
                    list.let {
                        binding.moviesRecyclerView.visibility = View.VISIBLE
                        movieAdapter?.updateList(ArrayList(it))
                    }
                }
            }

            launch {
                viewModel.error.collectLatest {
                    Log.i(TAG, "error collected: $it")
                    if (it.isNotEmpty()) {
                        binding.errorTryAgainLayout.visibility = View.VISIBLE
                        binding.errorTryAgainText.text = it
                        binding.moviesRecyclerView.visibility = View.GONE
                    }
                }
            }

            launch {
                viewModel.loading.collectLatest {
                    if (it) {
                        Log.i(TAG, "progressBar visible")
                        binding.progressBar.visibility = View.VISIBLE
                        binding.errorTryAgainLayout.visibility = View.GONE
                    } else {
                        Log.i(TAG, "progressBar not visible")
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun searchMovies() {

        binding.apply {

            searchButton.setOnClickListener {

                Log.i(TAG,"searchButton clicked")

                movieName = binding.editTextMovieName.text.toString()

                if (movieName.isNotEmpty()) {
                    viewModel.getMoviesList(movieName)
                } else {
                    Toast.makeText(requireContext(),"Please do not leave the movie search field blank",Toast.LENGTH_SHORT).show()
                }

            }

            editTextMovieName.setOnEditorActionListener { textView, i, keyEvent ->

                if (i == EditorInfo.IME_ACTION_DONE) {

                    movieName = binding.editTextMovieName.text.toString()

                    if (movieName.isNotEmpty()) {
                        viewModel.getMoviesList(movieName)
                    } else {
                        Toast.makeText(requireContext(),"Please do not leave the movie search field blank",Toast.LENGTH_SHORT).show()
                    }

                    true
                }
                false
            }
        }

    }

    private fun initializeRecyclerView() {

        movieAdapter = MovieAdapter(movieList)
        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRecyclerView.adapter = movieAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}