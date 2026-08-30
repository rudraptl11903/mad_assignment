package com.example.mad_assignment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
class ProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_progress); findViewById<View>(R.id.btnBack).setOnClickListener { finish() } }
}
