package com.example.mad_assignment

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TimetableActivity : AppCompatActivity() {

    private val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    private var startIndex = 0

    private lateinit var monday: TextView
    private lateinit var tuesday: TextView
    private lateinit var wednesday: TextView
    private lateinit var thursday: TextView
    private lateinit var friday: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)

        monday = findViewById(R.id.tvMonday)
        tuesday = findViewById(R.id.tvTuesday)
        wednesday = findViewById(R.id.tvWednesday)
        thursday = findViewById(R.id.tvThursday)
        friday = findViewById(R.id.tvFriday)

        val name = intent.getStringExtra("studentName").orEmpty().trim()
        findViewById<TextView>(R.id.tvStudentName).text =
            if (name.isBlank()) "Student Timetable" else "Student: $name"

        showTimetable()

        findViewById<Button>(R.id.btnRegenerate).setOnClickListener {
            startIndex = (startIndex + 1) % days.size
            showTimetable()
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    private fun showTimetable() {
        val views = arrayOf(monday, tuesday, wednesday, thursday, friday)
        val enteredSubjects = Array(5) { index ->
            intent.getStringExtra("subject${index + 1}").orEmpty().trim()
        }
        val nonBlankSubjects = enteredSubjects.filter { it.isNotEmpty() }

        for (i in views.indices) {
            val text = if (nonBlankSubjects.isEmpty()) {
                "Free / Self Study"
            } else {
                nonBlankSubjects[(i + startIndex) % nonBlankSubjects.size]
            }
            views[i].text = "${days[i]}\n$text"
        }
    }
}
