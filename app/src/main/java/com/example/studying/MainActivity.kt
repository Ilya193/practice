package com.example.studying

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding
import com.example.studying.databinding.AddUserLayoutBinding
import com.example.studying.databinding.InfoLayoutBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        createInfoLayout()
        createAddUserLayout()
    }

    private fun createInfoLayout() {
        val infoLayout = InfoLayoutBinding.inflate(layoutInflater)
        infoLayout.icExpand.setOnClickListener {
            with(infoLayout) {
                val state = if (container.visibility == View.GONE) View.VISIBLE else View.GONE
                val icon = if (state == View.VISIBLE) R.drawable.ic_expand_less else R.drawable.ic_expand_more
                container.visibility = state
                icExpand.setImageResource(icon)
            }
        }
        binding.mainContainer.addView(infoLayout.root)
    }

    private fun createAddUserLayout() {
        val addUserLayout = AddUserLayoutBinding.inflate(layoutInflater)
        addUserLayout.icAddUser.setOnClickListener {
            binding.mainContainer.removeView(addUserLayout.root)
            createInfoLayout()
            createAddUserLayout()
        }
        binding.mainContainer.addView(addUserLayout.root)
    }
}