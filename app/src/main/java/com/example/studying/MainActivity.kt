package com.example.studying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()

    private val adapter = InputAdapter(
        changeTextHidden = { position, newText ->
            viewModel.changeTextHidden(position, newText)
        },
        changeText = { position, newText ->
            viewModel.changeText(position, newText)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.list.setHasFixedSize(true)
        binding.list.adapter = adapter
        viewModel.uiState.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.coup()
    }
}