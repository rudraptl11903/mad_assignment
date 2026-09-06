package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val studentName = findViewById<EditText>(R.id.etStudentName)
        val subject1 = findViewById<EditText>(R.id.etSubject1)
        val subject2 = findViewById<EditText>(R.id.etSubject2)
        val subject3 = findViewById<EditText>(R.id.etSubject3)
        val subject4 = findViewById<EditText>(R.id.etSubject4)
        val subject5 = findViewById<EditText>(R.id.etSubject5)

        findViewById<Button>(R.id.btnGenerate).setOnClickListener {
            val subjects = arrayOf(
                subject1.text.toString().trim(),
                subject2.text.toString().trim(),
                subject3.text.toString().trim(),
                subject4.text.toString().trim(),
                subject5.text.toString().trim()
            )

            if (subjects.all { it.isEmpty() }) {
                Toast.makeText(
                    this,
                    "Please enter at least one subject",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val intent = Intent(this, TimetableActivity::class.java)
            intent.putExtra("studentName", studentName.text.toString().trim())

            for (i in subjects.indices) {
                intent.putExtra("subject${i + 1}", subjects[i])
            }

            startActivity(intent)
        }

        findViewById<Button>(R.id.btnAbout).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}
