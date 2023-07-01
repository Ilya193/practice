package com.example.studying.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.core.BaseTextWatcher
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

        binding.search.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()) {
                    viewModel.search(p0.toString())
                    enabledUi(false)
                }
                else {
                    viewModel.search(p0.toString())
                    enabledUi(true)
                }
            }
        })
    }

    private fun enabledUi(data: Boolean) {
        binding.saveButton.isEnabled = data
        binding.messageSave.isEnabled = data
    }

    override fun onClick(message: MessageUi) {
        viewModel.deleteMessage(message)
    }
}