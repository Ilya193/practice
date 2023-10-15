package com.example.studying

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.studying.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.inputText.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            viewModel.changeState(text.toString())
        })

        binding.inputText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.changeState(binding.inputText.text.toString())
            else viewModel.changeState("")
        }

        viewModel.uiState.observe(this) {
            when (it) {
                is StateInput.Success -> binding.inputLayout.error = null
                is StateInput.Error -> binding.inputLayout.error = it.msg
            }
        }
    }
}