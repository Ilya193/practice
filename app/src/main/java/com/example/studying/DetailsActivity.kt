package com.example.studying

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        findViewById<TextView>(R.id.textView).setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
    }
}