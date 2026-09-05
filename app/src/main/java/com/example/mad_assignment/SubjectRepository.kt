package com.example.mad_assignment

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

object SubjectRepository {
    private const val PREFS_NAME = "timetable_prefs"
    private const val KEY_SUBJECTS = "subjects"

    val WEEKDAYS = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    // Standard lecture time slots used by Rule-Based AI generator
    data class TimeSlot(val start: String, val end: String)
    val STANDARD_SLOTS = listOf(
        TimeSlot("09:00 AM", "10:00 AM"),
        TimeSlot("10:15 AM", "11:15 AM"),
        TimeSlot("11:30 AM", "12:30 PM"),
        TimeSlot("01:30 PM", "02:30 PM"),
        TimeSlot("02:45 PM", "03:45 PM")
    )

    fun getSubjects(context: Context): List<Subject> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonStr = prefs.getString(KEY_SUBJECTS, null) ?: return emptyList()
        val list = mutableListOf<Subject>()
        try {
            val jsonArray = JSONArray(jsonStr)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val s = Subject(
                    jsonObject.optString("name", ""),
                    jsonObject.optString("teacher", ""),
                    jsonObject.optString("day", ""),
                    jsonObject.optString("startTime", ""),
                    jsonObject.optString("endTime", ""),
                    jsonObject.optString("room", "")
                )
                list.add(s)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun saveSubjects(context: Context, subjects: List<Subject>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonArray = JSONArray()
        for (s in subjects) {
            val jsonObject = JSONObject().apply {
                put("name", s.name)
                put("teacher", s.teacher)
                put("day", s.day)
                put("startTime", s.startTime)
                put("endTime", s.endTime)
                put("room", s.room)
            }
            jsonArray.put(jsonObject)
        }
        prefs.edit().putString(KEY_SUBJECTS, jsonArray.toString()).apply()
    }

    fun addSubject(context: Context, s: Subject) {
        val list = getSubjects(context).toMutableList()
        list.add(s)
        saveSubjects(context, list)
    }

    fun deleteSubject(context: Context, index: Int) {
        val list = getSubjects(context).toMutableList()
        if (index in 0 until list.size) {
            list.removeAt(index)
            saveSubjects(context, list)
        }
    }

    fun clearSubjects(context: Context) {
        saveSubjects(context, emptyList())
    }

    fun getTodayName(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            Calendar.SUNDAY -> "Sunday"
            else -> "Monday"
        }
    }

    fun getTodaySubjects(context: Context): List<Subject> {
        val today = getTodayName()
        return getSubjects(context)
            .filter { it.day.equals(today, ignoreCase = true) }
            .sortedBy { it.startTime }
    }

    /**
     * Rule-Based AI Timetable Generator
     *
     * Applies rule-based heuristics:
     * 1. Assign subjects to predefined standard conflict-free time slots.
     * 2. Distribute subjects evenly across working weekdays (Mon - Fri).
     * 3. Avoid repeating the same subject on the same day where possible.
     * 4. Allocate consistent classrooms and teacher assignments.
     */
    fun generateRuleBasedTimetable(context: Context): List<Subject> {
        val currentSubjects = getSubjects(context)
        val pool = if (currentSubjects.isNotEmpty()) {
            // Deduplicate base subjects by name
            val distinctMap = LinkedHashMap<String, Pair<String, String>>()
            for (s in currentSubjects) {
                if (!distinctMap.containsKey(s.name)) {
                    distinctMap[s.name] = Pair(s.teacher, s.room)
                }
            }
            distinctMap.map { (name, info) ->
                Triple(name, info.first.ifEmpty { "Prof. Faculty" }, info.second.ifEmpty { "Room 101" })
            }
        } else {
            // Default curriculum if student has not added any subjects yet
            listOf(
                Triple("Mobile App Development", "Prof. Patel", "Lab-1"),
                Triple("Data Structures & Algorithms", "Prof. Sharma", "A-101"),
                Triple("Operating Systems", "Prof. Gupta", "B-202"),
                Triple("Database Management Systems", "Prof. Mehta", "Lab-2"),
                Triple("Computer Networks", "Prof. Rao", "C-303"),
                Triple("Software Engineering", "Prof. Joshi", "A-102")
            )
        }

        val generated = mutableListOf<Subject>()
        val weekdays = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
        val slotsPerDay = 3 // Standard 3 classes per day for a balanced academic schedule
        var subjectCursor = 0

        for (dayIndex in weekdays.indices) {
            val day = weekdays[dayIndex]
            val subjectsAssignedToday = mutableSetOf<String>()

            for (slotIndex in 0 until slotsPerDay) {
                val slot = STANDARD_SLOTS[slotIndex % STANDARD_SLOTS.size]
                
                // Rule: Pick next subject avoiding duplicate on the same day if possible
                var attempts = 0
                while (attempts < pool.size && subjectsAssignedToday.contains(pool[subjectCursor % pool.size].first)) {
                    subjectCursor++
                    attempts++
                }

                val chosen = pool[subjectCursor % pool.size]
                subjectsAssignedToday.add(chosen.first)
                subjectCursor++

                generated.add(
                    Subject(
                        name = chosen.first,
                        teacher = chosen.second,
                        day = day,
                        startTime = slot.start,
                        endTime = slot.end,
                        room = chosen.third
                    )
                )
            }
        }

        saveSubjects(context, generated)
        return generated
    }
}
