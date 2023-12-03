package com.example.studying

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.studying.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.init()

        viewModel.images.observe(this) { data ->
            val adapter = SliderAdapter(this, data)
            binding.viewPager.adapter = adapter
            binding.dotsIndicator.attachTo(binding.viewPager)

            binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val transform = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(30))
                addTransformer { page, position ->
                    page.scaleY = (0.85 + (1 - abs(position)) * 0.15f).toFloat()
                }
            }

            binding.viewPager.setPageTransformer(transform)
        }
    }
}