package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved dark mode preference
        val prefs = getSharedPreferences("timetable_prefs", MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        updateTodaySchedule()
    }

    private fun setupClickListeners() {
        // Quick Action Buttons
        findViewById<TextView>(R.id.btnAddSubject).setOnClickListener {
            startActivity(Intent(this, AddSubjectActivity::class.java))
        }

        findViewById<TextView>(R.id.btnTimetable).setOnClickListener {
            startActivity(Intent(this, TimetableActivity::class.java))
        }

        findViewById<TextView>(R.id.btnProgress).setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))
        }

        findViewById<TextView>(R.id.btnGenerate).setOnClickListener {
            showGenerateTimetableDialog()
        }

        // Today Schedule Click -> open timetable
        findViewById<TextView>(R.id.todayContainer).setOnClickListener {
            startActivity(Intent(this, TimetableActivity::class.java))
        }

        // Bottom Navigation Bar
        findViewById<TextView>(R.id.navHome).setOnClickListener {
            Toast.makeText(this, "You are on the Home screen", Toast.LENGTH_SHORT).show()
        }

        findViewById<TextView>(R.id.navTimetable).setOnClickListener {
            startActivity(Intent(this, TimetableActivity::class.java))
        }

        findViewById<TextView>(R.id.navSubjects).setOnClickListener {
            startActivity(Intent(this, SubjectsActivity::class.java))
        }

        findViewById<TextView>(R.id.navSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Top Bar Icons
        findViewById<TextView>(R.id.tvMenu).setOnClickListener {
            showQuickMenuDialog()
        }

        findViewById<TextView>(R.id.tvNotification).setOnClickListener {
            showNotificationsStatus()
        }
    }

    private fun updateTodaySchedule() {
        val todayContainer = findViewById<TextView>(R.id.todayContainer)
        val todaySubjects = SubjectRepository.getTodaySubjects(this)
        val todayName = SubjectRepository.getTodayName()

        if (todaySubjects.isEmpty()) {
            todayContainer.text = "No classes scheduled for today ($todayName).\nTap '+ Add Subject' or 'Generate' to plan."
        } else {
            val sb = StringBuilder()
            sb.append("Today ($todayName):\n")
            for (s in todaySubjects) {
                sb.append("• ${s.startTime} - ${s.endTime}: ${s.name} (${s.room})\n")
            }
            todayContainer.text = sb.toString().trim()
        }
    }

    private fun showGenerateTimetableDialog() {
        val currentCount = SubjectRepository.getSubjects(this).size
        val message = if (currentCount > 0) {
            "Rule-Based AI will distribute your $currentCount registered subjects into balanced, conflict-free time slots across Monday to Friday. Proceed?"
        } else {
            "No subjects found yet. Rule-Based AI will generate a standard semester timetable with 6 foundational subjects. Proceed?"
        }

        AlertDialog.Builder(this)
            .setTitle("AI Timetable Generator")
            .setMessage(message)
            .setPositiveButton("Generate") { _, _ ->
                SubjectRepository.generateRuleBasedTimetable(this)
                Toast.makeText(this, "Timetable generated successfully with Rule-Based AI!", Toast.LENGTH_LONG).show()
                updateTodaySchedule()
                startActivity(Intent(this, TimetableActivity::class.java))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showQuickMenuDialog() {
        val options = arrayOf("Add Subject", "View All Subjects", "View Timetable", "Progress & Analytics", "Settings")
        AlertDialog.Builder(this)
            .setTitle("AI Timetable Generator")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> startActivity(Intent(this, AddSubjectActivity::class.java))
                    1 -> startActivity(Intent(this, SubjectsActivity::class.java))
                    2 -> startActivity(Intent(this, TimetableActivity::class.java))
                    3 -> startActivity(Intent(this, ProgressActivity::class.java))
                    4 -> startActivity(Intent(this, SettingsActivity::class.java))
                }
            }
            .show()
    }

    private fun showNotificationsStatus() {
        val prefs = getSharedPreferences("timetable_prefs", MODE_PRIVATE)
        val enabled = prefs.getBoolean("notifications", true)
        val todayClasses = SubjectRepository.getTodaySubjects(this).size
        val status = if (enabled) "Enabled" else "Disabled"
        val message = "Class Reminders: $status\nToday's Classes: $todayClasses"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}