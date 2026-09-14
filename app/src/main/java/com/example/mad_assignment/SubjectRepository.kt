package com.example.mad_assignment

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object SubjectRepository {

    private const val PREFS_NAME = "timetable_prefs"
    private const val KEY_SUBJECTS = "subjects"

    // Monday to Saturday
    val DAYS = listOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    )

    // 5 predefined subjects
    val SUBJECTS = listOf(
        "Android Development",
        "Computer Network",
        "Data Analysis",
        "Machine Learning",
        "Python Programming"
    )

    // 3 lectures every day
    val START_TIMES = listOf(
        "08:30 AM",
        "09:45 AM",
        "10:30 AM"
    )

    val END_TIMES = listOf(
        "09:30 AM",
        "10:30 AM",
        "11:00 AM"
    )

    // 3 fixed classrooms
    val CLASSROOMS = listOf(
        "A-101",
        "A-102",
        "A-103"
    )

    fun getSubjects(context: Context): List<Subject> {

        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val jsonString = prefs.getString(
            KEY_SUBJECTS,
            null
        ) ?: return emptyList()

        val list = mutableListOf<Subject>()

        try {

            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {

                val obj = jsonArray.getJSONObject(i)

                list.add(
                    Subject(
                        name = obj.optString("name", ""),
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

    fun saveSubjects(
        context: Context,
        subjects: List<Subject>
    ) {

        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val jsonArray = JSONArray()

        for (subject in subjects) {

            val obj = JSONObject()

            obj.put("name", subject.name)
            obj.put("day", subject.day)
            obj.put("startTime", subject.startTime)
            obj.put("endTime", subject.endTime)
            obj.put("room", subject.room)

            jsonArray.put(obj)
        }

        prefs.edit()
            .putString(KEY_SUBJECTS, jsonArray.toString())
            .apply()
    }

    fun addSubject(
        context: Context,
        subjectName: String
    ): Boolean {

        val existingSubjects =
            getSubjects(context).toMutableList()

        // Maximum 18 lectures
        if (existingSubjects.size >= 18) {
            return false
        }

        val lectureNumber =
            existingSubjects.size

        val dayIndex =
            lectureNumber / 3

        val lectureIndex =
            lectureNumber % 3

        if (dayIndex >= DAYS.size) {
            return false
        }

        val newSubject = Subject(

            name = subjectName,

            day = DAYS[dayIndex],

            startTime = START_TIMES[lectureIndex],

            endTime = END_TIMES[lectureIndex],

            room = CLASSROOMS[lectureIndex]
        )

        existingSubjects.add(newSubject)

        saveSubjects(
            context,
            existingSubjects
        )

        return true
    }

    fun clearSubjects(context: Context) {

        saveSubjects(
            context,
            emptyList()
        )
    }
}