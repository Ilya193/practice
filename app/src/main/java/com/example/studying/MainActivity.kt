package com.example.studying

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var binder: BoundService.LocalBinder? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as BoundService.LocalBinder
            binder?.subscribe {
                if (binding.container.visibility == View.INVISIBLE) {
                    setVisibility(View.INVISIBLE)
                }
            }
            binder?.unsubscribe {
                if (binding.container.visibility == View.VISIBLE) {
                    setVisibility(View.VISIBLE)
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder?.unsubscribe {
                if (binding.container.visibility == View.VISIBLE) {
                    setVisibility(View.VISIBLE)
                }
            }
            binder = null
        }
    }

    private fun setVisibility(visibility: Int) {
        val animate = if (visibility == View.INVISIBLE) {
            binding.container.visibility = View.VISIBLE
            TranslateAnimation(0f, 0f, binding.container.height.toFloat(), 0f)
        }
        else {
            binding.container.visibility = View.INVISIBLE
            TranslateAnimation(0f, 0f, 0f, binding.container.height.toFloat())
        }
        animate.duration = 500
        animate.fillAfter = true
        binding.container.startAnimation(animate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.start.setOnClickListener {
            Intent(this, BoundService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
                intent.putExtra(BoundService.ACTION, BoundService.START_SERVICE)
                startService(intent)
            }
        }
        binding.stop.setOnClickListener {
            Intent(this, BoundService::class.java).also { intent ->
                unbindService(connection)
                intent.putExtra(BoundService.ACTION, BoundService.STOP_SERVICE)
                startService(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Intent(this, BoundService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onPause() {
        super.onPause()
        unbindService(connection)
    }
}