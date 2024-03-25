package com.example.arwa.movieapp.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.arwa.movieapp.R
import com.example.arwa.movieapp.core.utils.NetworkConnectivityObserver
import com.example.arwa.movieapp.core.utils.NetworkStatus
import com.example.arwa.movieapp.core.utils.handleVisibility
import com.example.arwa.movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeNetwork()


    }

    private fun observeNetwork() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                NetworkConnectivityObserver.observeNetworkConnection().collect {
                    when (it) {
                        NetworkStatus.LOST -> {
                            binding.testViewNoConnection.handleVisibility(true)
                            binding.testViewNoConnection.setText(R.string.network_is_lost)
                        }

                        NetworkStatus.UNAVAILABLE -> {
                            binding.testViewNoConnection.handleVisibility(true)
                            binding.testViewNoConnection.setText(R.string.network_is_unavailable)
                        }

                        else -> {
                            binding.testViewNoConnection.handleVisibility(false)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}