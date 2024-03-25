package com.example.arwa.movieapp.presentation.movies.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.arwa.movieapp.BuildConfig
import com.example.arwa.movieapp.databinding.ItemMovieBinding
import com.example.arwa.movieapp.domain.models.DomainMovies

class MoviesAdapter(private var clickListener: ((movieId: Int) -> Unit)? = null) :
    RecyclerView.Adapter<MoviesAdapter.Holder>() {
    private var moviesList: List<DomainMovies> = emptyList()

    fun setOnItemClick(clickListener: ((movieId: Int) -> Unit)) {
        this.clickListener = clickListener
    }

    fun setList(moviesList: List<DomainMovies>) {
        this.moviesList = moviesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder) {
            with(moviesList[position]) {
                binding.textViewMovieName.text = this.title
                binding.textViewReleasedDate.text = this.date
                binding.imageViewMoviePoster
                    .load(BuildConfig.IMAGE_BASE_URL.plus(this.posterPath)) {
                        crossfade(true)
                    }
                onItemClick(binding, moviesList[position].id)

            }
        }
    }

    private fun onItemClick(binding: ItemMovieBinding, movieId: Int) {
        clickListener?.let { listener ->
            binding.imageViewMoviePoster.setOnClickListener { listener(movieId) }
        }
    }

    inner class Holder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
}