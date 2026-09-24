package com.example.aitimetable

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {

    private lateinit var repository: SubjectRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        repository = SubjectRepository(this)

        initViews()
    }

    private fun initViews() {
        val prefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)

        val btnBack = findViewById<TextView>(R.id.btnBack)
        val switchDarkMode = findViewById<SwitchCompat>(R.id.switchDarkMode)
        val switchClassReminder = findViewById<SwitchCompat>(R.id.switchClassReminder)
        val btnLoadSample = findViewById<Button>(R.id.btnLoadSample)
        val btnClearData = findViewById<Button>(R.id.btnClearData)

        btnBack.setOnClickListener {
            finish()
        }

        // Dark Mode switch
        val isDark = prefs.getBoolean("dark_mode", false)
        switchDarkMode.isChecked = isDark
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Class Reminder switch
        val reminderEnabled = prefs.getBoolean("class_reminder", true)
        switchClassReminder.isChecked = reminderEnabled
        switchClassReminder.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("class_reminder", isChecked).apply()
            val msg = if (isChecked) "Class reminders enabled" else "Class reminders disabled"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        // Demo Data Controls
        btnLoadSample.setOnClickListener {
            repository.loadSampleSubjects()
            Toast.makeText(this, getString(R.string.toast_data_loaded), Toast.LENGTH_SHORT).show()
        }

        btnClearData.setOnClickListener {
            repository.clearSubjects()
            Toast.makeText(this, getString(R.string.toast_data_cleared), Toast.LENGTH_SHORT).show()
        }
    }
}
