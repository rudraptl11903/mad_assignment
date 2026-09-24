package com.example.aitimetable

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import java.util.Locale

class TimetableActivity : AppCompatActivity() {

    private lateinit var repository: SubjectRepository
    private lateinit var layoutEmptyTimetable: ConstraintLayout
    private lateinit var cardWeeklyGrid: ConstraintLayout
    private lateinit var tvDetailedHeading: TextView
    private lateinit var containerDaySchedule: ConstraintLayout

    private val daysOrder = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    private val pillColors = listOf(
        Pair(R.color.pill_ad_bg, R.color.pill_ad_text),
        Pair(R.color.pill_ml_bg, R.color.pill_ml_text),
        Pair(R.color.pill_cn_bg, R.color.pill_cn_text),
        Pair(R.color.pill_db_bg, R.color.pill_db_text),
        Pair(R.color.pill_os_bg, R.color.pill_os_text)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)

        repository = SubjectRepository(this)

        initViews()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        buildTimetable()
    }

    private fun initViews() {
        layoutEmptyTimetable = findViewById(R.id.layoutEmptyTimetable)
        cardWeeklyGrid = findViewById(R.id.cardWeeklyGrid)
        tvDetailedHeading = findViewById(R.id.tvDetailedHeading)
        containerDaySchedule = findViewById(R.id.containerDaySchedule)
    }

    private fun setupListeners() {
        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnEmptyAdd).setOnClickListener {
            startActivity(Intent(this, AddSubjectActivity::class.java))
        }
    }

    private fun buildTimetable() {
        val subjects = repository.getSubjects()

        if (subjects.isEmpty()) {
            layoutEmptyTimetable.visibility = View.VISIBLE
            cardWeeklyGrid.visibility = View.GONE
            tvDetailedHeading.visibility = View.GONE
            containerDaySchedule.visibility = View.GONE
            return
        }

        layoutEmptyTimetable.visibility = View.GONE
        cardWeeklyGrid.visibility = View.VISIBLE
        tvDetailedHeading.visibility = View.VISIBLE
        containerDaySchedule.visibility = View.VISIBLE

        clearGridCells()
        populateGrid(subjects)
        populateDetailedCards(subjects)
    }

    private fun clearGridCells() {
        val days = listOf("mon", "tue", "wed", "thu", "fri", "sat")
        val slots = listOf("9", "10", "11", "12", "1", "2", "3")

        for (d in days) {
            for (s in slots) {
                val cellId = resources.getIdentifier("cell_${d}_${s}", "id", packageName)
                if (cellId != 0) {
                    val tv = findViewById<TextView?>(cellId)
                    tv?.text = ""
                    tv?.background = null
                }
            }
        }
    }

    private fun populateGrid(subjects: List<Subject>) {
        for (i in subjects.indices) {
            val s = subjects[i]
            val dayKey = when (s.day.trim().lowercase(Locale.ROOT)) {
                "monday" -> "mon"
                "tuesday" -> "tue"
                "wednesday" -> "wed"
                "thursday" -> "thu"
                "friday" -> "fri"
                "saturday" -> "sat"
                else -> null
            } ?: continue

            val hour = extractHour(s.startTime)
            val slotKey = when (hour) {
                9 -> "9"
                10 -> "10"
                11 -> "11"
                12 -> "12"
                1, 13 -> "1"
                2, 14 -> "2"
                3, 15 -> "3"
                else -> null
            } ?: continue

            val cellId = resources.getIdentifier("cell_${dayKey}_${slotKey}", "id", packageName)
            if (cellId != 0) {
                val cell = findViewById<TextView?>(cellId)
                if (cell != null) {
                    cell.text = getInitials(s.subjectName)
                    cell.setBackgroundResource(R.drawable.bg_card)
                    val colorPair = pillColors[i % pillColors.size]
                    cell.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, colorPair.first))
                    cell.setTextColor(ContextCompat.getColor(this, colorPair.second))
                }
            }
        }
    }

    private fun populateDetailedCards(subjects: List<Subject>) {
        containerDaySchedule.removeAllViews()

        val grouped = subjects.groupBy { it.day.trim().lowercase(Locale.ROOT) }
        val sortedSubjects = mutableListOf<Subject>()

        for (day in daysOrder) {
            val list = grouped[day.lowercase(Locale.ROOT)]
            if (!list.isNullOrEmpty()) {
                val daySorted = list.sortedBy { parseTimeToMinutes(it.startTime) }
                sortedSubjects.addAll(daySorted)
            }
        }

        if (sortedSubjects.isEmpty()) return

        val createdCards = mutableListOf<ConstraintLayout>()

        for (s in sortedSubjects) {
            val card = ConstraintLayout(this).apply {
                id = View.generateViewId()
                setBackgroundResource(R.drawable.bg_card)
                elevation = 4f
                setPadding(36, 28, 36, 28)
            }

            // Day Badge
            val tvDayBadge = TextView(this).apply {
                id = View.generateViewId()
                text = s.day.uppercase(Locale.ROOT)
                textSize = 11f
                setTypeface(null, Typeface.BOLD)
                setTextColor(ContextCompat.getColor(context, R.color.primary_blue))
            }
            card.addView(tvDayBadge)

            // Time Range
            val tvTimeRange = TextView(this).apply {
                id = View.generateViewId()
                text = "${s.startTime} - ${s.endTime}"
                textSize = 13f
                setTypeface(null, Typeface.BOLD)
                setTextColor(ContextCompat.getColor(context, R.color.text_dark))
            }
            card.addView(tvTimeRange)

            // Subject Name
            val tvSubject = TextView(this).apply {
                id = View.generateViewId()
                text = s.subjectName
                textSize = 15f
                setTypeface(null, Typeface.BOLD)
                setTextColor(ContextCompat.getColor(context, R.color.primary_blue))
            }
            card.addView(tvSubject)

            // Teacher Name
            val tvTeacher = TextView(this).apply {
                id = View.generateViewId()
                text = s.teacherName
                textSize = 13f
                setTextColor(ContextCompat.getColor(context, R.color.text_gray))
            }
            card.addView(tvTeacher)

            // Room Number
            val tvRoom = TextView(this).apply {
                id = View.generateViewId()
                text = s.roomNumber
                textSize = 12f
                setTextColor(ContextCompat.getColor(context, R.color.text_dark))
            }
            card.addView(tvRoom)

            // Constraints inside card
            val innerSet = ConstraintSet()
            innerSet.clone(card)

            innerSet.connect(tvDayBadge.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            innerSet.connect(tvDayBadge.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

            innerSet.connect(tvTimeRange.id, ConstraintSet.TOP, tvDayBadge.id, ConstraintSet.BOTTOM, 6)
            innerSet.connect(tvTimeRange.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

            innerSet.connect(tvSubject.id, ConstraintSet.TOP, tvTimeRange.id, ConstraintSet.BOTTOM, 4)
            innerSet.connect(tvSubject.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

            innerSet.connect(tvTeacher.id, ConstraintSet.TOP, tvSubject.id, ConstraintSet.BOTTOM, 4)
            innerSet.connect(tvTeacher.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

            innerSet.connect(tvRoom.id, ConstraintSet.TOP, tvTeacher.id, ConstraintSet.BOTTOM, 4)
            innerSet.connect(tvRoom.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            innerSet.connect(tvRoom.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            innerSet.applyTo(card)

            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 12, 0, 12)
            }
            card.layoutParams = params

            containerDaySchedule.addView(card)
            createdCards.add(card)
        }

        // Chain cards vertically in containerDaySchedule
        val outerSet = ConstraintSet()
        outerSet.clone(containerDaySchedule)

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

        outerSet.applyTo(containerDaySchedule)
    }

    private fun extractHour(timeStr: String): Int {
        return try {
            val clean = timeStr.trim().uppercase(Locale.ROOT)
            val isPM = clean.contains("PM")
            val isAM = clean.contains("AM")
            val parts = clean.replace("AM", "").replace("PM", "").trim().split(":")
            var hours = parts[0].toInt()
            if (isPM && hours < 12) hours += 12
            if (isAM && hours == 12) hours = 0
            hours
        } catch (e: Exception) {
            9
        }
    }

    private fun parseTimeToMinutes(timeStr: String): Int {
        return try {
            val clean = timeStr.trim().uppercase(Locale.ROOT)
            val isPM = clean.contains("PM")
            val isAM = clean.contains("AM")
            val parts = clean.replace("AM", "").replace("PM", "").trim().split(":")
            var hours = parts[0].toInt()
            val minutes = if (parts.size > 1) parts[1].toInt() else 0
            if (isPM && hours < 12) hours += 12
            if (isAM && hours == 12) hours = 0
            hours * 60 + minutes
        } catch (e: Exception) {
            0
        }
    }

    private fun getInitials(name: String): String {
        val words = name.trim().split(" ").filter { it.isNotEmpty() }
        return when {
            words.size >= 2 -> "${words[0].first()}${words[1].first()}".uppercase(Locale.ROOT)
            words.size == 1 && words[0].length >= 2 -> words[0].substring(0, 2).uppercase(Locale.ROOT)
            words.size == 1 -> words[0].uppercase(Locale.ROOT)
            else -> "CL"
        }
    }
}
