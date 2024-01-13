package com.example.studying

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.studying.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Подтверждение отпечатка пальца")
                    .setSubtitle("Пожалуйста, подтвердите свой отпечаток пальца")
                    .setNegativeButtonText("Отмена")
                    .build()

                val biometricPrompt: BiometricPrompt? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                println("s149 onAuthenticationSucceeded")
                            }

                            override fun onAuthenticationFailed() {
                                super.onAuthenticationFailed()
                                println("s149 onAuthenticationFailed")
                            }

                            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                super.onAuthenticationError(errorCode, errString)
                                println("s149 onAuthenticationError")
                            }
                        })
                    } else {
                        null
                    }

                biometricPrompt?.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                println("s149 BIOMETRIC_ERROR_NO_HARDWARE")

            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                println("s149 BIOMETRIC_ERROR_HW_UNAVAILABLE")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                println("s149 BIOMETRIC_ERROR_NONE_ENROLLED")
            }
        }
    }
}