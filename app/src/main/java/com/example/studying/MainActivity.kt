package com.example.studying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*binding.customView.setOnClickListener { view ->
            val valueAnimator = ValueAnimator.ofFloat(0f, 3600f)
            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                view.rotation = value
            }
            valueAnimator.interpolator = AnticipateOvershootInterpolator()
            valueAnimator.duration = 1500
            valueAnimator.start()
        }*/

        binding.clear.setOnClickListener {
            binding.customView.clear()
        }

        binding.undo.setOnClickListener {
            binding.customView.undo()
        }

        binding.save.setOnClickListener {
            binding.customView.save()
        }
    }
}