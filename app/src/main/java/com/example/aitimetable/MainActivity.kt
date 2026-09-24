package com.example.aitimetable

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var repository: SubjectRepository
    private lateinit var tvTodayClasses: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = SubjectRepository(this)

        initViews()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadTodaySchedule()
    }

    private fun initViews() {
        tvTodayClasses = findViewById(R.id.tvTodayClasses)
    }

    private fun setupListeners() {
        findViewById<TextView>(R.id.btnQuickAdd).setOnClickListener {
            startActivity(Intent(this, AddSubjectActivity::class.java))
        }

        // 4 Main Dashboard Action Cards
        findViewById<ConstraintLayout>(R.id.cardActionAddSubject).setOnClickListener {
            startActivity(Intent(this, AddSubjectActivity::class.java))
        }

        findViewById<ConstraintLayout>(R.id.cardActionTimetable).setOnClickListener {
            startActivity(Intent(this, TimetableActivity::class.java))
        }

        findViewById<ConstraintLayout>(R.id.cardActionProgress).setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))
        }

        findViewById<ConstraintLayout>(R.id.cardActionGenerate).setOnClickListener {
            generateTimetable()
        }

        // Bottom Navigation Bar
        findViewById<ConstraintLayout>(R.id.navBtnHome).setOnClickListener {
            loadTodaySchedule()
        }

        findViewById<ConstraintLayout>(R.id.navBtnTimetable).setOnClickListener {
            startActivity(Intent(this, TimetableActivity::class.java))
        }

        findViewById<ConstraintLayout>(R.id.navBtnSubjects).setOnClickListener {
            startActivity(Intent(this, SubjectsActivity::class.java))
        }

        findViewById<ConstraintLayout>(R.id.navBtnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    /**
     * Rule-based AI Timetable Generator:
     * 1. Read saved subjects.
     * 2. Check if subjects exist; if none, show warning toast.
     * 3. Group subjects by day and sort chronologically by start time.
     * 4. Show success message and navigate to TimetableActivity.
     */
    private fun generateTimetable() {
        val subjects = repository.getSubjects()
        if (subjects.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_no_subjects), Toast.LENGTH_SHORT).show()
        } else {
            val sorted = subjects.sortedWith(
                compareBy(
                    { getDayOrder(it.day) },
                    { parseTimeToMinutes(it.startTime) }
                )
            )
            repository.saveSubjects(sorted)
            Toast.makeText(this, getString(R.string.toast_timetable_generated), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, TimetableActivity::class.java))
        }
    }

    private fun loadTodaySchedule() {
        val subjects = repository.getSubjects()
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val currentDay = dayFormat.format(calendar.time)

        val todaysClasses = subjects.filter { it.day.equals(currentDay, ignoreCase = true) }
            .sortedBy { parseTimeToMinutes(it.startTime) }

        if (todaysClasses.isEmpty()) {
            tvTodayClasses.text = getString(R.string.no_classes_today)
        } else {
            val builder = StringBuilder()
            todaysClasses.forEachIndexed { index, s ->
                builder.append("${s.startTime} - ${s.endTime}: ${s.subjectName} (${s.roomNumber})")
                if (index < todaysClasses.size - 1) {
                    builder.append("\n\n")
                }
            }
            tvTodayClasses.text = builder.toString()
        }
    }

    private fun getDayOrder(day: String): Int {
        return when (day.trim().lowercase(Locale.ROOT)) {
            "monday" -> 1
            "tuesday" -> 2
            "wednesday" -> 3
            "thursday" -> 4
            "friday" -> 5
            "saturday" -> 6
            "sunday" -> 7
            else -> 8
        }
    }

    private fun parseTimeToMinutes(timeStr: String): Int {
        return try {
            val clean = timeStr.trim().uppercase(Locale.ROOT)
            val isPM = clean.contains("PM")
            val isAM = clean.contains("AM")
            val parts = clean.replace("AM", "").replace("PM", "").trim().split(":")
            var hours = parts[0].toInt()
            val minutes = if (parts.size > 1) parts[1].toInt() else 0
            if (isPM && hours < 12) hours += 12
            if (isAM && hours == 12) hours = 0
            hours * 60 + minutes
        } catch (e: Exception) {
            0
        }
    }
}
