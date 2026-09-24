package com.example.aitimetable

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class SubjectRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("timetable_prefs", Context.MODE_PRIVATE)

    fun addSubject(subject: Subject) {
        val subjects = getSubjects().toMutableList()
        subjects.add(subject)
        saveSubjects(subjects)
    }

    fun getSubjects(): List<Subject> {
        val jsonString = prefs.getString("subjects", null) ?: return emptyList()
        val jsonArray = JSONArray(jsonString)
        val subjects = mutableListOf<Subject>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            subjects.add(
                Subject(
                    id = obj.optLong("id", System.currentTimeMillis()),
                    subjectName = obj.optString("subjectName", ""),
                    teacherName = obj.optString("teacherName", ""),
                    day = obj.optString("day", "Monday"),
                    startTime = obj.optString("startTime", "09:00"),
                    endTime = obj.optString("endTime", "10:00"),
                    roomNumber = obj.optString("roomNumber", "")
                )
            )
        }
        return subjects
    }

    fun saveSubjects(subjects: List<Subject>) {
        val jsonArray = JSONArray()
        subjects.forEach {
            val obj = JSONObject()
            obj.put("id", it.id)
            obj.put("subjectName", it.subjectName)
            obj.put("teacherName", it.teacherName)
            obj.put("day", it.day)
            obj.put("startTime", it.startTime)
            obj.put("endTime", it.endTime)
            obj.put("roomNumber", it.roomNumber)
            jsonArray.put(obj)
        }
        prefs.edit().putString("subjects", jsonArray.toString()).apply()
    }

    fun deleteSubject(id: Long) {
        val updated = getSubjects().filter { it.id != id }
        saveSubjects(updated)
    }

    fun clearSubjects() {
        prefs.edit().remove("subjects").apply()
    }

    fun loadSampleSubjects() {
        val samples = listOf(
            Subject(1, "Android Development", "Prof. Patel", "Monday", "09:00", "10:00", "Room A-101"),
            Subject(2, "Machine Learning", "Prof. Shah", "Tuesday", "11:00", "12:00", "Room B-202"),
            Subject(3, "Computer Networks", "Prof. Mehta", "Wednesday", "10:00", "11:00", "Room C-303"),
            Subject(4, "Database Systems", "Prof. Desai", "Thursday", "02:00", "03:00", "Room D-404"),
            Subject(5, "Operating Systems", "Prof. Joshi", "Friday", "01:00", "02:00", "Room E-505")
        )
        saveSubjects(samples)
    }
}
