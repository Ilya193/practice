package com.example.studying.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()
    private val adapter = PostsAdapter { title ->
        Snackbar.make(binding.root, title, Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding.rvPosts.adapter = adapter
        binding.rvPosts.setHasFixedSize(true)

        mainViewModel.fetchPosts()

        mainViewModel.uiState.observe(this) { state ->
            when (state) {
                is PostUiState.Success -> showPosts(state.data)
                is PostUiState.Error -> showError(state.message)
                is PostUiState.Loading -> showLoading()
            }
        }
    }

    private fun showPosts(data: List<PostUi.Success>) {
        binding.progressBar.visibility = View.GONE
        binding.containerError.visibility = View.GONE
        binding.rvPosts.visibility = View.VISIBLE

        adapter.submitList(data)
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.rvPosts.visibility = View.GONE
        binding.containerError.visibility = View.VISIBLE

        binding.tvError.text = message
        binding.btnRetry.setOnClickListener {
            mainViewModel.fetchPosts()
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }
}