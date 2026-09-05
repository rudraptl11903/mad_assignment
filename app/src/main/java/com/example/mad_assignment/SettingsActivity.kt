package com.example.mad_assignment

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        val switchDark = findViewById<CompoundButton>(R.id.switchDark)
        val switchNotification = findViewById<CompoundButton>(R.id.switchNotification)
        val tvAbout = findViewById<TextView>(R.id.tvAbout)
        val prefs = getSharedPreferences("timetable_prefs", MODE_PRIVATE)

        val isDarkMode = prefs.getBoolean("dark_mode", false)
        switchDark.isChecked = isDarkMode
        switchDark.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val isNotificationEnabled = prefs.getBoolean("notifications", true)
        switchNotification.isChecked = isNotificationEnabled
        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications", isChecked).apply()
            val status = if (isChecked) "enabled" else "disabled"
            Toast.makeText(this, "Class reminders $status", Toast.LENGTH_SHORT).show()
        }

        tvAbout.text = "ABOUT APP\n\n" +
                "Project: AI Timetable Generator\n" +
                "Author: Rudra Patel\n" +
                "Version: 1.0\n" +
                "Architecture: Kotlin + XML\n" +
                "Engine: Rule-Based AI\n" +
                "Mode: 100% Offline (No Firebase, No External API)"
    }
}
