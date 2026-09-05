package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class TimetableActivity : AppCompatActivity() {

    // Filter mode: "WEEKLY", "TODAY", or a specific day like "Monday"
    private var currentFilter: String = "TODAY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<View>(R.id.btnAutoGenerate).setOnClickListener {
            showGenerateConfirmDialog()
        }

        val dayTabs = findViewById<TextView>(R.id.dayTabs)
        dayTabs.setOnClickListener {
            showDayFilterDialog()
        }

        val btnWeekly = findViewById<Button>(R.id.btnWeekly)
        btnWeekly.setOnClickListener {
            currentFilter = if (currentFilter == "WEEKLY") "TODAY" else "WEEKLY"
            updateTimetableDisplay()
        }

        updateTimetableDisplay()
    }

    override fun onResume() {
        super.onResume()
        updateTimetableDisplay()
    }

    private fun showDayFilterDialog() {
        val days = arrayOf("Today", "Full Weekly Timetable", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        AlertDialog.Builder(this)
            .setTitle("Filter Timetable by Day")
            .setItems(days) { _, which ->
                currentFilter = when (which) {
                    0 -> "TODAY"
                    1 -> "WEEKLY"
                    else -> days[which]
                }
                updateTimetableDisplay()
            }
            .show()
    }

    private fun showGenerateConfirmDialog() {
        val count = SubjectRepository.getSubjects(this).size
        val prompt = if (count > 0) {
            "Regenerate timetable using Rule-Based AI?\n\nThis will organize your subjects into standard conflict-free time slots across Monday to Friday."
        } else {
            "Generate academic timetable using Rule-Based AI?\n\nA complete semester curriculum with 6 standard subjects will be scheduled automatically."
        }

        AlertDialog.Builder(this)
            .setTitle("Rule-Based AI Timetable Generator")
            .setMessage(prompt)
            .setPositiveButton("Generate") { _, _ ->
                SubjectRepository.generateRuleBasedTimetable(this)
                Toast.makeText(this, "Timetable generated with Rule-Based AI!", Toast.LENGTH_SHORT).show()
                currentFilter = "WEEKLY"
                updateTimetableDisplay()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateTimetableDisplay() {
        val container = findViewById<TextView>(R.id.timetableContainer)
        val btnWeekly = findViewById<Button>(R.id.btnWeekly)
        val dayTabs = findViewById<TextView>(R.id.dayTabs)
        val subjects = SubjectRepository.getSubjects(this)

        if (subjects.isEmpty()) {
            container.text = "Your timetable is currently empty.\n\n" +
                    "• Tap '⚡ AI Generate' above to auto-create a balanced schedule.\n" +
                    "• Or tap 'Add Subject' on the Home screen to add your own courses."
            btnWeekly.text = "⚡ GENERATE WITH AI"
            btnWeekly.setOnClickListener { showGenerateConfirmDialog() }
            dayTabs.text = "Tap here to select day"
            return
        }

        btnWeekly.setOnClickListener {
            currentFilter = if (currentFilter == "WEEKLY") "TODAY" else "WEEKLY"
            updateTimetableDisplay()
        }

        when (currentFilter) {
            "WEEKLY" -> {
                dayTabs.text = "ALL DAYS  ▼  (Tap to filter)"
                btnWeekly.text = "VIEW TODAY'S SCHEDULE"

                val weekdays = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
                val sb = StringBuilder()
                sb.append("WEEKLY TIMETABLE (${subjects.size} Total Classes)\n")
                sb.append("═".repeat(36) + "\n\n")

                for (day in weekdays) {
                    val daySubjects = subjects.filter { it.day.equals(day, ignoreCase = true) }
                        .sortedBy { it.startTime }
                    if (daySubjects.isNotEmpty()) {
                        sb.append("📅 $day (${daySubjects.size} Classes)\n")
                        sb.append("─".repeat(30) + "\n")
                        for (s in daySubjects) {
                            sb.append("⏰ ${s.startTime} - ${s.endTime}\n")
                            sb.append("   📖 ${s.name}\n")
                            sb.append("   👨‍🏫 ${s.teacher} | 📍 Room: ${s.room}\n\n")
                        }
                    }
                }
                container.text = sb.toString().trim()
            }

            "TODAY" -> {
                val todayName = SubjectRepository.getTodayName()
                dayTabs.text = "$todayName (Today)  ▼"
                btnWeekly.text = "VIEW FULL WEEKLY TIMETABLE"

                val todaySubjects = SubjectRepository.getTodaySubjects(this)
                val sb = StringBuilder()
                sb.append("TODAY'S SCHEDULE - $todayName\n")
                sb.append("═".repeat(36) + "\n\n")

                if (todaySubjects.isEmpty()) {
                    sb.append("No classes scheduled for today ($todayName).\nEnjoy your free time or prepare for upcoming classes!")
                } else {
                    for (s in todaySubjects) {
                        sb.append("⏰ ${s.startTime} - ${s.endTime}\n")
                        sb.append("   📖 ${s.name}\n")
                        sb.append("   👨‍🏫 ${s.teacher} | 📍 Room: ${s.room}\n\n")
                    }
                }
                container.text = sb.toString().trim()
            }

            else -> {
                // Specific day filter
                val selectedDay = currentFilter
                dayTabs.text = "$selectedDay  ▼  (Tap to change)"
                btnWeekly.text = "VIEW FULL WEEKLY TIMETABLE"

                val daySubjects = subjects.filter { it.day.equals(selectedDay, ignoreCase = true) }
                    .sortedBy { it.startTime }
                val sb = StringBuilder()
                sb.append("SCHEDULE FOR $selectedDay\n")
                sb.append("═".repeat(36) + "\n\n")

                if (daySubjects.isEmpty()) {
                    sb.append("No classes scheduled for $selectedDay.")
                } else {
                    for (s in daySubjects) {
                        sb.append("⏰ ${s.startTime} - ${s.endTime}\n")
                        sb.append("   📖 ${s.name}\n")
                        sb.append("   👨‍🏫 ${s.teacher} | 📍 Room: ${s.room}\n\n")
                    }
                }
                container.text = sb.toString().trim()
            }
        }
    }
}
