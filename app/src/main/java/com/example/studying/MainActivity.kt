package com.example.studying

import android.content.ClipData.Item
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter = RangeAdapter { index, item ->
        for (i in 0..index) {
            list[i] = ItemUi(id = list[i].id, text = list[i].text, checked = !item.checked)
        }

        submitList()
    }

    private val list = mutableListOf<ItemUi>().apply {
        for (i in 0..20) {
            add(ItemUi(i, text = "hello world $i"))
        }
    }

    private fun submitList(): Unit = adapter.submitList(list.toList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvMessage.adapter = adapter
        binding.rvMessage.setHasFixedSize(true)
        adapter.submitList(list.toList())
    }
}