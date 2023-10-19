package com.example.studying.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MessagesViewModel by viewModel()
    private val adapter = MessagesAdapter(::onClick)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        settingRecyclerView()
        settingViewModel()
        settingListeners()
    }

    private fun settingRecyclerView() {
        binding.messagesRV.adapter = adapter
        binding.messagesRV.setHasFixedSize(true)
        binding.messagesRV.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    private fun settingViewModel() {
        viewModel.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun settingListeners() {
        binding.saveButton.setOnClickListener {
            viewModel.saveMessage(binding.messageSave.text.toString())
            binding.messageSave.setText("")
        }

        binding.search.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            viewModel.search(text.toString())
            if (text.toString().isNotEmpty()) enabledUi(false)
            else enabledUi(true)
        })
    }

    private fun enabledUi(data: Boolean) {
        binding.saveButton.isEnabled = data
        binding.messageSave.isEnabled = data
    }

    private fun onClick(message: MessageUi) {
        viewModel.deleteMessage(message)
    }
}