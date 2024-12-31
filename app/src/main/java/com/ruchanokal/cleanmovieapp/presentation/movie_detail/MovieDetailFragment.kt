package com.ruchanokal.cleanmovieapp.presentation.movie_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ruchanokal.cleanmovieapp.R
import com.ruchanokal.cleanmovieapp.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val TAG = "MovieDetailFragment"
    private var _binding : FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            val imdbId = MovieDetailFragmentArgs.fromBundle(it).imdbId
            Log.i(TAG,"imdbId: $imdbId")
            viewModel.getMovieDetail(imdbId)
            observeDatas()

        }

    }

    private fun observeDatas() {

        binding.apply {

            viewLifecycleOwner.lifecycleScope.launch {

                launch {
                    viewModel.movie.collectLatest {
                        contentLayout.visibility = View.VISIBLE
                        moviedetail = it
                    }
                }

                launch {
                    viewModel.error.collectLatest {
                        contentLayout.visibility = View.GONE
                        errorText.visibility = View.VISIBLE
                        Log.i(TAG, "error message: $it")
                    }
                }

                launch {
                    viewModel.loading.collectLatest {
                        if (it){
                            progressBar.visibility = View.VISIBLE
                            contentLayout.visibility = View.GONE
                            errorText.visibility = View.GONE
                        } else {
                            progressBar.visibility = View.GONE
                        }
                    }
                }

            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}