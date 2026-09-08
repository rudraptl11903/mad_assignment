package com.example.mad_assignment

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object SubjectRepository {
    private const val PREFS_NAME = "timetable_prefs"
    private const val KEY_SUBJECTS = "subjects"

    val DAYS = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    fun getSubjects(context: Context): List<Subject> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonStr = prefs.getString(KEY_SUBJECTS, null) ?: return emptyList()
        val list = mutableListOf<Subject>()
        try {
            val jsonArray = JSONArray(jsonStr)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    Subject(
                        name = obj.optString("name", ""),
                        teacher = obj.optString("teacher", ""),
                        day = obj.optString("day", ""),
                        startTime = obj.optString("startTime", ""),
                        endTime = obj.optString("endTime", ""),
                        room = obj.optString("room", "")
                    )
                )
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
            val obj = JSONObject().apply {
                put("name", s.name)
                put("teacher", s.teacher)
                put("day", s.day)
                put("startTime", s.startTime)
                put("endTime", s.endTime)
                put("room", s.room)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString(KEY_SUBJECTS, jsonArray.toString()).apply()
    }

    fun addSubject(context: Context, subject: Subject) {
        val list = getSubjects(context).toMutableList()
        list.add(subject)
        saveSubjects(context, list)
    }

    fun clearSubjects(context: Context) {
        saveSubjects(context, emptyList())
    }
}
