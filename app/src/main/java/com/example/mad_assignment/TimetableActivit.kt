package com.example.mad_assignment

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class TimetableActivity : AppCompatActivity() {
    private var isShowingWeekly = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }

        val btnWeekly = findViewById<Button>(R.id.btnWeekly)
        btnWeekly.setOnClickListener {
            isShowingWeekly = !isShowingWeekly
            updateTimetableDisplay()
        }

        updateTimetableDisplay()
    }

    private fun updateTimetableDisplay() {
        val container = findViewById<TextView>(R.id.timetableContainer)
        val btnWeekly = findViewById<Button>(R.id.btnWeekly)
        val subjects = SubjectRepository.getSubjects(this)

        if (subjects.isEmpty()) {
            container.text = "Your timetable is empty.\nPlease add subjects in the 'Add Subject' tab."
            btnWeekly.text = "VIEW WEEKLY TIMETABLE"
            return
        }

        if (isShowingWeekly) {
            val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
            val sb = StringBuilder()
            sb.append("WEEKLY TIMETABLE\n\n")
            for (day in days) {
                val daySubjects = subjects.filter { it.day.equals(day, ignoreCase = true) }
                    .sortedBy { it.startTime }
                if (daySubjects.isNotEmpty()) {
                    sb.append("=== $day ===\n")
                    for (s in daySubjects) {
                        sb.append("• ${s.startTime} - ${s.endTime}: ${s.name} (Room ${s.room})\n")
                    }
                    sb.append("\n")
                }
            }
            container.text = sb.toString().trim()
            btnWeekly.text = "VIEW TODAY'S TIMETABLE"
        } else {
            val calendar = Calendar.getInstance()
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val currentDayString = when (dayOfWeek) {
                Calendar.MONDAY -> "Monday"
                Calendar.TUESDAY -> "Tuesday"
                Calendar.WEDNESDAY -> "Wednesday"
                Calendar.THURSDAY -> "Thursday"
                Calendar.FRIDAY -> "Friday"
                Calendar.SATURDAY -> "Saturday"
                Calendar.SUNDAY -> "Sunday"
                else -> ""
            }

            val todaySubjects = subjects.filter { it.day.equals(currentDayString, ignoreCase = true) }
                .sortedBy { it.startTime }

            val sb = StringBuilder()
            sb.append("TODAY'S TIMETABLE ($currentDayString)\n\n")
            if (todaySubjects.isEmpty()) {
                sb.append("No classes scheduled for today.")
            } else {
                for (s in todaySubjects) {
                    sb.append("• ${s.startTime} - ${s.endTime}: ${s.name}\n")
                    sb.append("  Teacher: ${s.teacher} | Room: ${s.room}\n\n")
                }
            }
            container.text = sb.toString().trim()
            btnWeekly.text = "VIEW WEEKLY TIMETABLE"
        }
    }
}
