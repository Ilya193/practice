package com.example.studying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()
    private val adapter = NotesAdapter {
        viewModel.favorite(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.sendBtn.setOnClickListener {
            val text = binding.input.text.toString()
            if (text.isNotEmpty()) viewModel.addNote(text)
            binding.input.setText("")
        }

        binding.list.adapter = adapter
        binding.list.setHasFixedSize(true)

        viewModel.uiState.observe(this) {
            adapter.submitList(it)
        }

        viewModel.fetchNotes()
    }
}