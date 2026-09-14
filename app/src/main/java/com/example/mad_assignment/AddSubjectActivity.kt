package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddSubjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_subject)

        // Back button
        findViewById<View>(R.id.btnBack).setOnClickListener {

            finish()
        }

        // Subject Spinner
        val subjectSpinner =
            findViewById<Spinner>(R.id.spSubject)

        val subjectAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            SubjectRepository.SUBJECTS
        )

        subjectAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        subjectSpinner.adapter = subjectAdapter

        // Add button
        val btnSave =
            findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {

            val selectedSubject =
                subjectSpinner.selectedItem
                    ?.toString()
                    ?.trim()
                    ?: ""

            if (selectedSubject.isEmpty()) {

                showMessage(
                    "Please select a subject"
                )

                return@setOnClickListener
            }

            // Check whether subject already exists
            val alreadyExists =
                SubjectRepository
                    .getSubjects(this)
                    .any {
                        it.name.equals(
                            selectedSubject,
                            ignoreCase = true
                        )
                    }

            if (alreadyExists) {

                showMessage(
                    "$selectedSubject is already added"
                )

                return@setOnClickListener
            }

            // Add subject
            val added =
                SubjectRepository.addSubject(
                    this,
                    selectedSubject
                )

            if (!added) {

                showMessage(
                    "Maximum 18 lectures can be added"
                )

                return@setOnClickListener
            }

            showMessage(
                "$selectedSubject added successfully"
            )

            // Open timetable
            val intent = Intent(
                this,
                TimetableActivity::class.java
            )

            startActivity(intent)

            finish()
        }
    }

    private fun showMessage(message: String) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}