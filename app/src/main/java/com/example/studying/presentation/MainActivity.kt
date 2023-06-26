package com.example.studying.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.core.MessagesAdapter
import com.example.studying.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), Listeners {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MessagesViewModel by viewModel()
    private val adapter = MessagesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.messagesRV.adapter = adapter
        binding.messagesRV.setHasFixedSize(true)
        binding.messagesRV.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        viewModel.observe(this) {
            adapter.submitList(it)
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveMessage(binding.messageSave.text.toString())
        }
    }

    override fun onClick(message: MessageUi) {
        viewModel.deleteMessage(message)
    }
}