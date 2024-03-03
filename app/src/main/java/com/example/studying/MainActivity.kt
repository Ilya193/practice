package com.example.studying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var index = 0
    private val adapter = MainAdapter((0..50).map {
        it.toString()
    }, change = {
        change(it)
    })

    private fun change(position: Int) {
        binding.recycler.post {
            adapter.notifyItemChanged(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recycler.layoutManager = CustomLinearLayoutManager(this)

        binding.recycler.adapter = adapter

        binding.up.setOnClickListener {
            if (index > 0) {
                adapter.mode = false
                binding.recycler.scrollToPosition(--index)
            }
        }

        binding.down.setOnClickListener {
            adapter.mode = true
            binding.recycler.scrollToPosition(++index)
        }
    }
}