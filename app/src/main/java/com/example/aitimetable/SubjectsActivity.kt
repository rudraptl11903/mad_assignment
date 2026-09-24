package com.example.aitimetable

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat

class SubjectsActivity : AppCompatActivity() {

    private lateinit var repository: SubjectRepository
    private lateinit var containerSubjects: ConstraintLayout
    private lateinit var layoutEmptyState: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects)

        repository = SubjectRepository(this)

        containerSubjects = findViewById(R.id.containerSubjects)
        layoutEmptyState = findViewById(R.id.layoutEmptyState)

        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        displaySubjects()
    }

    private fun setupListeners() {
        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btnAddTop).setOnClickListener {
            startActivity(Intent(this, AddSubjectActivity::class.java))
        }

        findViewById<Button>(R.id.btnEmptyAdd).setOnClickListener {
            startActivity(Intent(this, AddSubjectActivity::class.java))
        }
    }

    private fun displaySubjects() {
        val subjects = repository.getSubjects()

        containerSubjects.removeAllViews()

        if (subjects.isEmpty()) {
            layoutEmptyState.visibility = View.VISIBLE
            containerSubjects.addView(layoutEmptyState)

            val constraintSet = ConstraintSet()
            constraintSet.clone(containerSubjects)
            constraintSet.connect(layoutEmptyState.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            constraintSet.connect(layoutEmptyState.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            constraintSet.connect(layoutEmptyState.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            constraintSet.connect(layoutEmptyState.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            constraintSet.applyTo(containerSubjects)
            return
        }

        layoutEmptyState.visibility = View.GONE

        val createdCards = mutableListOf<ConstraintLayout>()

        for (subject in subjects) {
            val card = ConstraintLayout(this).apply {
                id = View.generateViewId()
                setBackgroundResource(R.drawable.bg_card)
                elevation = 4f
                setPadding(36, 32, 36, 32)
            }

            // Subject Name
            val tvName = TextView(this).apply {
                id = View.generateViewId()
                text = subject.subjectName
                textSize = 16f
                setTypeface(null, Typeface.BOLD)
                setTextColor(ContextCompat.getColor(context, R.color.text_dark))
            }
            card.addView(tvName)

            // Teacher Name
            val tvTeacher = TextView(this).apply {
                id = View.generateViewId()
                text = subject.teacherName
                textSize = 13f
                setTextColor(ContextCompat.getColor(context, R.color.text_gray))
            }
            card.addView(tvTeacher)

            // Day & Time
            val tvDayTime = TextView(this).apply {
                id = View.generateViewId()
                text = "${subject.day} | ${subject.startTime} - ${subject.endTime}"
                textSize = 13f
                setTextColor(ContextCompat.getColor(context, R.color.primary_blue))
                setTypeface(null, Typeface.BOLD)
            }
            card.addView(tvDayTime)

            // Room Number
            val tvRoom = TextView(this).apply {
                id = View.generateViewId()
                text = subject.roomNumber
                textSize = 12f
                setTextColor(ContextCompat.getColor(context, R.color.text_dark))
            }
            card.addView(tvRoom)

            // Delete Action
            val btnDelete = TextView(this).apply {
                id = View.generateViewId()
                text = "Remove"
                textSize = 12f
                setTypeface(null, Typeface.BOLD)
                setTextColor(ContextCompat.getColor(context, R.color.notification_red))
                setPadding(16, 8, 16, 8)
                setOnClickListener {
                    repository.deleteSubject(subject.id)
                    Toast.makeText(this@SubjectsActivity, "${subject.subjectName} removed", Toast.LENGTH_SHORT).show()
                    displaySubjects()
                }
            }
            card.addView(btnDelete)

            // Constraints inside card
            val innerSet = ConstraintSet()
            innerSet.clone(card)

            innerSet.connect(tvName.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            innerSet.connect(tvName.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            innerSet.connect(tvName.id, ConstraintSet.END, btnDelete.id, ConstraintSet.START)

            innerSet.connect(btnDelete.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            innerSet.connect(btnDelete.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

            innerSet.connect(tvTeacher.id, ConstraintSet.TOP, tvName.id, ConstraintSet.BOTTOM, 6)
            innerSet.connect(tvTeacher.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

            innerSet.connect(tvDayTime.id, ConstraintSet.TOP, tvTeacher.id, ConstraintSet.BOTTOM, 8)
            innerSet.connect(tvDayTime.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

            innerSet.connect(tvRoom.id, ConstraintSet.TOP, tvDayTime.id, ConstraintSet.BOTTOM, 4)
            innerSet.connect(tvRoom.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            innerSet.connect(tvRoom.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            innerSet.applyTo(card)

            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            card.layoutParams = params

            containerSubjects.addView(card)
            createdCards.add(card)
        }

        // Chain cards vertically in containerSubjects
        val outerSet = ConstraintSet()
        outerSet.clone(containerSubjects)

        for (i in createdCards.indices) {
            val current = createdCards[i]
            outerSet.connect(current.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            outerSet.connect(current.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

            if (i == 0) {
                outerSet.connect(current.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            } else {
                outerSet.connect(current.id, ConstraintSet.TOP, createdCards[i - 1].id, ConstraintSet.BOTTOM)
            }

            if (i == createdCards.size - 1) {
                outerSet.connect(current.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            }
        }

        outerSet.applyTo(containerSubjects)
    }
}
