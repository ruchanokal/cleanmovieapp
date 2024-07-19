package com.ruchanokal.cleanmovieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ruchanokal.cleanmovieapp.R
import com.ruchanokal.cleanmovieapp.databinding.MovieRowBinding
import com.ruchanokal.cleanmovieapp.domain.model.Movie
import com.ruchanokal.cleanmovieapp.presentation.movie.MovieFragmentDirections

class MovieAdapter(val movieList : ArrayList<Movie>)  : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    class MovieHolder(val binding: MovieRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = DataBindingUtil.inflate<MovieRowBinding>(LayoutInflater.from(parent.context), R.layout.movie_row,parent,false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        val movieItem = movieList[position]
        holder.binding.apply { movie = movieItem }

        holder.itemView.setOnClickListener {

            val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movieItem.imdbID)
            Navigation.findNavController(it).navigate(action)

        }

    }

   fun updateList(newList: List<Movie>) {
        movieList.clear()
        movieList.addAll(newList)
        notifyDataSetChanged()
    }
}