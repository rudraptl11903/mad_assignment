package com.example.aitimetable

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.Calendar
import java.util.Locale

class AddSubjectActivity : AppCompatActivity() {

    private lateinit var repository: SubjectRepository

    private lateinit var etSubjectName: EditText
    private lateinit var etTeacherName: EditText
    private lateinit var spDay: Spinner
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var etRoomNumber: EditText
    private lateinit var btnAddSubject: Button

    private val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subject)

        repository = SubjectRepository(this)

        initViews()
        setupSpinner()
        setupTimePickers()
        setupListeners()
    }

    private fun initViews() {
        etSubjectName = findViewById(R.id.etSubjectName)
        etTeacherName = findViewById(R.id.etTeacherName)
        spDay = findViewById(R.id.spDay)
        tvStartTime = findViewById(R.id.tvStartTime)
        tvEndTime = findViewById(R.id.tvEndTime)
        etRoomNumber = findViewById(R.id.etRoomNumber)
        btnAddSubject = findViewById(R.id.btnAddSubject)
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, days)
        spDay.adapter = adapter
    }

    private fun setupTimePickers() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        findViewById<ConstraintLayout>(R.id.layoutStartTime).setOnClickListener {
            val picker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val formatted = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                tvStartTime.text = formatted
            }, 9, 0, true)
            picker.show()
        }

        findViewById<ConstraintLayout>(R.id.layoutEndTime).setOnClickListener {
            val picker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val formatted = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                tvEndTime.text = formatted
            }, 10, 0, true)
            picker.show()
        }
    }

    private fun setupListeners() {
        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        btnAddSubject.setOnClickListener {
            val name = etSubjectName.text.toString().trim()
            val teacher = etTeacherName.text.toString().trim()
            val day = spDay.selectedItem?.toString() ?: "Monday"
            val start = tvStartTime.text.toString().trim()
            val end = tvEndTime.text.toString().trim()
            val room = etRoomNumber.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_enter_subject_name), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val finalStartTime = if (start.isEmpty()) "09:00" else start
            val finalEndTime = if (end.isEmpty()) "10:00" else end
            val finalTeacher = if (teacher.isEmpty()) "Faculty" else teacher
            val finalRoom = if (room.isEmpty()) "Room A-101" else room

            val newSubject = Subject(
                id = System.currentTimeMillis(),
                subjectName = name,
                teacherName = finalTeacher,
                day = day,
                startTime = finalStartTime,
                endTime = finalEndTime,
                roomNumber = finalRoom
            )

            repository.addSubject(newSubject)
            Toast.makeText(this, getString(R.string.toast_subject_added), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
