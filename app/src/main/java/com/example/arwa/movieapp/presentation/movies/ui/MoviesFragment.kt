package com.example.arwa.movieapp.presentation.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.arwa.movieapp.core.resource.ScreenUiState
import com.example.arwa.movieapp.core.utils.handleVisibility
import com.example.arwa.movieapp.databinding.FragmentMoviesBinding
import com.example.arwa.movieapp.presentation.movies.view_model.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var adapter: MoviesAdapter
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MoviesAdapter()
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(binding.recyclerMovies)
        getData()
        onClick(binding.root)

    }

    private fun getData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieFlow.collect {
                    when (it) {
                        is ScreenUiState.Loading -> {
                            binding.progress.handleVisibility(true)
                        }

                        is ScreenUiState.Success -> {
                            binding.progress.handleVisibility(false)
                            val data = it.data
                            adapter.setList(data)
                            binding.recyclerMovies.adapter = adapter
                        }

                        is ScreenUiState.Error -> {
                            binding.progress.handleVisibility(false)
                            Toast.makeText(
                                requireActivity().applicationContext,
                                it.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun onClick(view: View) {
        adapter.setOnItemClick { movieId ->
            val action =
                MoviesFragmentDirections
                    .actionMoviesFragmentToMovieDetailsFragment(movieId)
            view.findNavController().navigate(action)
        }
    }

}