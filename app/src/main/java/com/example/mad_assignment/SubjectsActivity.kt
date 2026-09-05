package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SubjectsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<View>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, AddSubjectActivity::class.java))
        }

        findViewById<View>(R.id.btnClearAll).setOnClickListener {
            confirmClearAll()
        }

        findViewById<TextView>(R.id.subjectContainer).setOnClickListener {
            showSubjectManagementDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        displaySubjects()
    }

    private fun displaySubjects() {
        val subjects = SubjectRepository.getSubjects(this)
        val container = findViewById<TextView>(R.id.subjectContainer)

        if (subjects.isEmpty()) {
            container.text = "No subjects added yet.\n\nTap '+ ADD SUBJECT' below to add your courses, or use 'Generate' from the home screen for Rule-Based AI timetable creation."
        } else {
            val sb = StringBuilder()
            sb.append("Total Subjects: ${subjects.size}\n")
            sb.append("Tap anywhere on this list to delete an individual subject.\n\n")
            for ((index, s) in subjects.withIndex()) {
                sb.append("${index + 1}. ${s.name}\n")
                sb.append("   • Instructor: ${s.teacher}\n")
                sb.append("   • Schedule: ${s.day} (${s.startTime} - ${s.endTime})\n")
                sb.append("   • Classroom: ${s.room}\n\n")
            }
            container.text = sb.toString().trim()
        }
    }

    private fun showSubjectManagementDialog() {
        val subjects = SubjectRepository.getSubjects(this)
        if (subjects.isEmpty()) return

        val subjectNames = subjects.mapIndexed { idx, s -> "${idx + 1}. ${s.name} (${s.day})" }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Manage Subject")
            .setItems(subjectNames) { _, which ->
                val chosen = subjects[which]
                AlertDialog.Builder(this)
                    .setTitle(chosen.name)
                    .setMessage("Day: ${chosen.day}\nTime: ${chosen.startTime} - ${chosen.endTime}\nTeacher: ${chosen.teacher}\nRoom: ${chosen.room}\n\nDo you want to delete this subject?")
                    .setPositiveButton("Delete") { _, _ ->
                        SubjectRepository.deleteSubject(this, which)
                        Toast.makeText(this, "${chosen.name} deleted", Toast.LENGTH_SHORT).show()
                        displaySubjects()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun confirmClearAll() {
        val subjects = SubjectRepository.getSubjects(this)
        if (subjects.isEmpty()) {
            Toast.makeText(this, "No subjects to clear", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(this)
            .setTitle("Clear All Subjects")
            .setMessage("Are you sure you want to remove all ${subjects.size} subjects from your timetable?")
            .setPositiveButton("Clear All") { _, _ ->
                SubjectRepository.clearSubjects(this)
                displaySubjects()
                Toast.makeText(this, "All subjects cleared", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
