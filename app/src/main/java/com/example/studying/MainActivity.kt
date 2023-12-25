package com.example.studying

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.elveum.elementadapter.simpleAdapter
import com.example.studying.databinding.ActivityMainBinding
import com.example.studying.databinding.ItemBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private val adapter = simpleAdapter<ItemUi, ItemBinding> {
        areContentsSame = { oldItem, newItem ->
            oldItem == newItem
        }

        areContentsSame = {oldItem, newItem ->
            oldItem == newItem
        }

        bind {
            image.setImageResource(it.value)
            image.visibility = if (it.visible) View.VISIBLE else View.INVISIBLE
            root.isClickable = !it.visible
        }

        listeners {
            root.onClick {
                viewModel.setVisible(index(), it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.items.adapter = adapter
        viewModel.init()
        binding.root.setOnClickListener {
            viewModel.init()
        }
        viewModel.items.observe(this) {
            adapter.submitList(it)
        }
        viewModel.game.observe(this) {
            when (it) {
                is GameUi.Tick -> {
                    binding.time.text = it.time
                    binding.money.text = it.money
                }
                is GameUi.Finish -> {

                }
            }
        }
    }
}