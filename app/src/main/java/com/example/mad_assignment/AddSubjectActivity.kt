package com.example.mad_assignment

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AddSubjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subject)

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val daySpinner = findViewById<Spinner>(R.id.spDay)
        daySpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        findViewById<Button>(R.id.btnStartTime).setOnClickListener { view ->
            pickTime(view as Button)
        }

        findViewById<Button>(R.id.btnEndTime).setOnClickListener { view ->
            pickTime(view as Button)
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val subject = findViewById<EditText>(R.id.etSubject).text.toString().trim()
            val teacher = findViewById<EditText>(R.id.etTeacher).text.toString().trim()
            val room = findViewById<EditText>(R.id.etRoom).text.toString().trim()
            val day = findViewById<Spinner>(R.id.spDay).selectedItem?.toString() ?: "Monday"
            val startTime = findViewById<Button>(R.id.btnStartTime).text.toString().trim()
            val endTime = findViewById<Button>(R.id.btnEndTime).text.toString().trim()

            when {
                subject.isEmpty() -> showMessage("Enter subject name")
                teacher.isEmpty() -> showMessage("Enter teacher name")
                room.isEmpty() -> showMessage("Enter room number")
                else -> {
                    val s = Subject(subject, teacher, day, startTime, endTime, room)
                    SubjectRepository.addSubject(this, s)
                    showMessage("$subject added successfully")
                    finish()
                }
            }
        }
    }

    private fun pickTime(button: Button) {
        val calendar = Calendar.getInstance()

        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                button.text = String.format("%02d:%02d", hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
