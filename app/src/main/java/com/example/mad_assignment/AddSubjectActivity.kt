package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddSubjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subject)

        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val daySpinner = findViewById<Spinner>(R.id.spDay)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            SubjectRepository.DAYS
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = adapter

        val etSubject = findViewById<EditText>(R.id.etSubject)
        val etTeacher = findViewById<EditText>(R.id.etTeacher)
        val etStartTime = findViewById<EditText>(R.id.etStartTime)
        val etEndTime = findViewById<EditText>(R.id.etEndTime)
        val etRoom = findViewById<EditText>(R.id.etRoom)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val subjectName = etSubject.text.toString().trim()
            val teacherName = etTeacher.text.toString().trim()
            val day = daySpinner.selectedItem?.toString() ?: "Monday"
            val startTime = etStartTime.text.toString().trim()
            val endTime = etEndTime.text.toString().trim()
            val room = etRoom.text.toString().trim()

            when {
                subjectName.isEmpty() -> {
                    showMessage("Please enter subject name")
                    etSubject.requestFocus()
                }
                teacherName.isEmpty() -> {
                    showMessage("Please enter teacher name")
                    etTeacher.requestFocus()
                }
                startTime.isEmpty() -> {
                    showMessage("Please enter start time")
                    etStartTime.requestFocus()
                }
                endTime.isEmpty() -> {
                    showMessage("Please enter end time")
                    etEndTime.requestFocus()
                }
                room.isEmpty() -> {
                    showMessage("Please enter room number")
                    etRoom.requestFocus()
                }
                else -> {
                    val newSubject = Subject(
                        name = subjectName,
                        teacher = teacherName,
                        day = day,
                        startTime = startTime,
                        endTime = endTime,
                        room = room
                    )
                    SubjectRepository.addSubject(this, newSubject)
                    showMessage("$subjectName added successfully")

                    val intent = Intent(this, TimetableActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
