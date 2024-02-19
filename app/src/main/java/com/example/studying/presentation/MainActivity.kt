package com.example.studying.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()
    private val adapter = NotesAdapter {
        viewModel.favorite(it)
    }

    private val adapterDelegate = NotesAdapterWithDelegate(favorite = {
        viewModel.favorite(it)
    }, delete = {
        viewModel.delete(it)
    }, detail = {
        DetailFragment.newInstance(it.text, it.id).show(supportFragmentManager, null)
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.sendBtn.setOnClickListener {
            val text = binding.input.text.toString()
            if (text.isNotEmpty()) viewModel.addNote(text)
            binding.input.setText("")
        }

        adapterDelegate.addDelegate(AdapterDelegate.Note())
        adapterDelegate.addDelegate(AdapterDelegate.Header())

        binding.list.adapter = adapterDelegate
        binding.list.setHasFixedSize(true)

        viewModel.uiState.observe(this) {
            adapterDelegate.submitList(it)
        }

        viewModel.fetchNotes()
    }
}