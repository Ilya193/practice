package com.example.studying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.studying.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.internal.common.CrashlyticsCore
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val crashlytics = Firebase.crashlytics
        val analytics = Firebase.analytics

        binding.root.setOnClickListener {
            try {
                throw RuntimeException("Test")
            } catch (e: Exception) {
                println("s149 $e")
                analytics.logEvent("TEST_EVENT", bundleOf("INIT" to 10))
                crashlytics.recordException(e)
            }
        }
    }
}