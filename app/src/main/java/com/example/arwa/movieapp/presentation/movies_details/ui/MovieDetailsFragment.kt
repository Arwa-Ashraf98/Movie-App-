package com.example.arwa.movieapp.presentation.movies_details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.arwa.movieapp.BuildConfig
import com.example.arwa.movieapp.R
import com.example.arwa.movieapp.core.resource.ScreenUiState
import com.example.arwa.movieapp.core.utils.handleVisibility
import com.example.arwa.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.arwa.movieapp.domain.models.DomainMovie
import com.example.arwa.movieapp.presentation.movies_details.view_model.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var movieId: Int? = 0
    private lateinit var binding: FragmentMovieDetailsBinding
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            movieId = MovieDetailsFragmentArgs.fromBundle(requireArguments()).id
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailsViewModel.getMovie(movieId ?: 0)
        observeState()

    }

    private fun observeState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailsViewModel.movieFlow.collect {
                    when (it) {
                        is ScreenUiState.Loading -> {
                            binding.progress.handleVisibility(true)
                            binding.group.handleVisibility(false)
                            binding.groupError.handleVisibility(false)
                        }

                        is ScreenUiState.Success -> {
                            binding.progress.handleVisibility(false)
                            binding.group.handleVisibility(true)
                            binding.groupError.handleVisibility(false)
                            initUi(it.data)
                        }

                        is ScreenUiState.Error -> {
                            binding.progress.handleVisibility(false)
                            binding.group.handleVisibility(false)
                            binding.groupError.handleVisibility(true)
                            binding.textViewError.text = it.error
                        }
                    }
                }
            }
        }
    }

    private fun initUi(movieDetails: DomainMovie) {
        binding.imageViewMoviePoster
            .load(BuildConfig.IMAGE_BASE_URL.plus(movieDetails.posterPath)) {
                placeholder(R.drawable.ic_launcher_background)
                crossfade(true)
            }

        binding.textViewMovieDescription.text = movieDetails.overview
        binding.textViewMovieName.text = movieDetails.title
        binding.textViewMovieTimeWatch.text = getString(R.string.watches).plus(movieDetails.runtime.toString())
        binding.textViewMoviePopularity.text =
            requireActivity().getText(movieDetailsViewModel.checkPopularity(movieDetails.popularity.toInt()))
        binding.textViewMovieGenre.text = movieDetails.genre.joinToString(", ") { it.name }
    }


}