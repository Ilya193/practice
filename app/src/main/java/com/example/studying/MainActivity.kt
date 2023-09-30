package com.example.studying

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.studying.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //mainViewModel.fetchMessages()
/*        findViewById<TextView>(R.id.tvHello).setOnClickListener {
            mainViewModel.fetchMessages()
        }*/

        findViewById<ConstraintLayout>(R.id.root).setOnClickListener {
            mainViewModel.createMessageWithFirestore("message")
        }
    }
}