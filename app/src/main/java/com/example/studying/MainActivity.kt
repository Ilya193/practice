package com.example.studying

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), Listeners {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, RegistrationFragment.newInstance())
            .addToBackStack(null)
            .commit()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isRegistrationFragment()) {
                    setDisplayHomeAsUpEnabled(false)
                    supportFragmentManager.popBackStack()
                }
                else if (isLoginFragment())
                    supportFragmentManager.popBackStack()
                else
                    finish()
            }
        })
    }

    private fun setDisplayHomeAsUpEnabled(data: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(data)
    }

    private fun isRegistrationFragment(): Boolean =
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) is RegistrationFragment

    private fun isLoginFragment(): Boolean =
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) is LoginFragment


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (isRegistrationFragment()) {
                    setDisplayHomeAsUpEnabled(false)
                    supportFragmentManager.popBackStack()
                }
                else {
                    supportFragmentManager.popBackStack()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCLick(data: String) {
        Snackbar.make(binding.root, data, Snackbar.LENGTH_LONG).show()
    }
}