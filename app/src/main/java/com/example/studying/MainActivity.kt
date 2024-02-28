package com.example.studying

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.studying.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            val animatorSet = AnimatorSet()
            val scaleDownX = ObjectAnimator.ofFloat(binding.img, "scaleX", 0.4f)
            val scaleDownY = ObjectAnimator.ofFloat(binding.img, "scaleY", 0.4f)
            scaleDownX.duration = 50
            scaleDownY.duration = 50

            val scaleUpX = ObjectAnimator.ofFloat(binding.img, "scaleX", 1f)
            val scaleUpY = ObjectAnimator.ofFloat(binding.img, "scaleY", 1f)
            scaleUpX.duration = 50
            scaleUpY.duration = 50

            val scaleDown = AnimatorSet()
            scaleDown.play(scaleDownX).with(scaleDownY)

            val scaleUp = AnimatorSet()
            scaleUp.play(scaleUpX).with(scaleUpY)

            val colorFrom =
                ContextCompat.getColor(this, if (state == 0) R.color.red else R.color.black)
            val colorTo =
                ContextCompat.getColor(this, if (state == 1) R.color.black else R.color.red)
            val colorAnimation = ValueAnimator.ofArgb(colorFrom, colorTo)
            colorAnimation.duration = 50
            colorAnimation.addUpdateListener { animator ->
                binding.img.setColorFilter(animator.animatedValue as Int, PorterDuff.Mode.SRC_IN)
            }
            animatorSet.playSequentially(scaleDown, colorAnimation, scaleUp)
            state = if (state == 0) 1 else 0
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            animatorSet.start()
        }
    }

    private var state = 0
}