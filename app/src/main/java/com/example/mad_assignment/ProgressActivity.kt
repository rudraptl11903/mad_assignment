package com.example.mad_assignment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        displayProgress()
    }

    override fun onResume() {
        super.onResume()
        displayProgress()
    }

    private fun displayProgress() {
        val container = findViewById<TextView>(R.id.progressContainer)
        val subjects = SubjectRepository.getSubjects(this)

        if (subjects.isEmpty()) {
            container.text = "No timetable data available.\n\n" +
                    "Add subjects or use 'Generate' from the home screen to view your academic workload and study progress analytics."
            return
        }

        val totalClasses = subjects.size
        val distinctSubjects = subjects.map { it.name }.distinct()
        val distinctCount = distinctSubjects.size
        val totalHours = totalClasses * 1.0 // Standard 1 hr per slot

        val sb = StringBuilder()
        sb.append("ACADEMIC PROGRESS & WORKLOAD\n")
        sb.append("═".repeat(36) + "\n\n")

        sb.append("📊 Summary Overview:\n")
        sb.append("• Registered Courses: $distinctCount\n")
        sb.append("• Total Weekly Lectures: $totalClasses\n")
        sb.append("• Estimated Weekly Hours: ${String.format("%.1f", totalHours)} hrs\n")
        sb.append("• Average Load/Day: ${String.format("%.1f", totalClasses / 5.0)} lectures\n\n")

        sb.append("📅 Day-wise Distribution:\n")
        val weekdays = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        for (day in weekdays) {
            val count = subjects.count { it.day.equals(day, ignoreCase = true) }
            val bar = "█".repeat(count)
            sb.append("• ${day.padEnd(10)}: $count class(es) $bar\n")
        }
        sb.append("\n")

        sb.append("📚 Subject Breakdown & Target Completion:\n")
        sb.append("─".repeat(36) + "\n")
        val defaultPercentages = listOf(85, 75, 90, 70, 80, 65, 88)

        for ((idx, subjectName) in distinctSubjects.withIndex()) {
            val count = subjects.count { it.name.equals(subjectName, ignoreCase = true) }
            val pct = defaultPercentages[idx % defaultPercentages.size]
            val filled = pct / 10
            val empty = 10 - filled
            val progressBar = "█".repeat(filled) + "░".repeat(empty)

            sb.append("${idx + 1}. $subjectName\n")
            sb.append("   Workload: $count lecture(s)/week\n")
            sb.append("   Progress: [$progressBar] $pct%\n\n")
        }

        sb.append("💡 Study Tip: Keep regular attendance and maintain at least 75% progress across all subjects.")
        container.text = sb.toString().trim()
    }
}
