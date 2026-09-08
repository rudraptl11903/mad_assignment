package com.example.mad_assignment

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class TimetableActivity : AppCompatActivity() {

    private lateinit var llTimetableContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)

        llTimetableContainer = findViewById(R.id.llTimetableContainer)

        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnAddMore).setOnClickListener {
            startActivity(Intent(this, AddSubjectActivity::class.java))
        }

        populateTimetable()
    }

    override fun onResume() {
        super.onResume()
        populateTimetable()
    }

    private fun populateTimetable() {
        llTimetableContainer.removeAllViews()

        val subjects = SubjectRepository.getSubjects(this)

        for (day in SubjectRepository.DAYS) {
            val dayCard = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundResource(R.drawable.bg_card)
                val pad = dpToPx(16)
                setPadding(pad, pad, pad, pad)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 0, 0, dpToPx(12))
                layoutParams = params
            }

            // Day Header
            val tvDay = TextView(this).apply {
                text = day
                textSize = 18f
                setTypeface(typeface, Typeface.BOLD)
                setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
            dayCard.addView(tvDay)

            // Divider
            val divider = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dpToPx(1)
                ).apply {
                    setMargins(0, dpToPx(8), 0, dpToPx(10))
                }
                setBackgroundColor(ContextCompat.getColor(context, R.color.border))
            }
            dayCard.addView(divider)

            val daySubjects = subjects.filter { it.day.equals(day, ignoreCase = true) }

            if (daySubjects.isEmpty()) {
                val tvEmpty = TextView(this).apply {
                    text = "No classes scheduled"
                    textSize = 14f
                    setTextColor(ContextCompat.getColor(context, R.color.gray))
                    setPadding(0, dpToPx(4), 0, dpToPx(4))
                }
                dayCard.addView(tvEmpty)
            } else {
                for ((index, subject) in daySubjects.withIndex()) {
                    if (index > 0) {
                        val innerDivider = View(this).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                dpToPx(1)
                            ).apply {
                                setMargins(0, dpToPx(8), 0, dpToPx(8))
                            }
                            setBackgroundColor(ContextCompat.getColor(context, R.color.border))
                        }
                        dayCard.addView(innerDivider)
                    }

                    val subjectLayout = LinearLayout(this).apply {
                        orientation = LinearLayout.VERTICAL
                    }

                    val tvName = TextView(this).apply {
                        text = subject.name
                        textSize = 16f
                        setTypeface(typeface, Typeface.BOLD)
                        setTextColor(ContextCompat.getColor(context, R.color.text))
                    }
                    subjectLayout.addView(tvName)

                    val tvTime = TextView(this).apply {
                        text = "${subject.startTime} - ${subject.endTime}"
                        textSize = 14f
                        setTypeface(typeface, Typeface.BOLD)
                        setTextColor(ContextCompat.getColor(context, R.color.blue))
                    }
                    subjectLayout.addView(tvTime)

                    val tvTeacher = TextView(this).apply {
                        text = subject.teacher
                        textSize = 14f
                        setTextColor(ContextCompat.getColor(context, R.color.text))
                    }
                    subjectLayout.addView(tvTeacher)

                    val tvRoom = TextView(this).apply {
                        text = "Room: ${subject.room}"
                        textSize = 14f
                        setTextColor(ContextCompat.getColor(context, R.color.gray))
                    }
                    subjectLayout.addView(tvRoom)

                    dayCard.addView(subjectLayout)
                }
            }

            llTimetableContainer.addView(dayCard)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
