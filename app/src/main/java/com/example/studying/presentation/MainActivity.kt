package com.example.studying.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.calendar.setOnDateChangeListener { view, year, month, day ->
            binding.empty.setOnClickListener(null)
            var newDay = day.toString()
            if (day < 10) newDay = "0$newDay"
            var newMonth = "${month + 1}"
            if (newMonth.toInt() < 10) newMonth = "0$newMonth"
            binding.date.text = "$newDay.$newMonth.$year"
            viewModel.date(newDay, newMonth, year.toString())
        }

        viewModel.date.observe(this) {
            binding.empty.setOnClickListener {}
            binding.empty.visibility = if (it is DateUiState.Loading) View.VISIBLE else View.GONE
            binding.loading.visibility = if (it is DateUiState.Loading) View.VISIBLE else View.GONE
            if (it is DateUiState.Success) binding.date.text = binding.date.text.toString() + " - ${it.result}"
            else if (it is DateUiState.Error) binding.date.text = it.msg
        }
    }
}