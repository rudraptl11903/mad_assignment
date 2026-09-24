package com.example.aitimetable

data class Subject(
    val id: Long = System.currentTimeMillis(),
    val subjectName: String,
    val teacherName: String,
    val day: String,
    val startTime: String,
    val endTime: String,
    val roomNumber: String
)
