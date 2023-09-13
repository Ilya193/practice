package com.example.studying.presentation

import android.os.Bundle
import android.util.Log
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

        mainViewModel.success.observe(this) {
            adapter.submitList(it)
        }

        mainViewModel.error.observe(this) {
            it.getContentOrNot {
                Log.w("attadag", it.toString())
            }
        }
    }
}