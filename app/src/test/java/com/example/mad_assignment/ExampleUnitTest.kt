package com.example.mad_assignment

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun timetable_cyclesEnteredSubjectsCorrectly() {
        val entered = arrayOf("Maths", "Physics", "", "", "")
        val valid = entered.filter { it.isNotBlank() }
        val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

        assertEquals(2, valid.size)

        // Day 0..4 distribution with startIndex = 0
        val scheduleDay0 = Array(5) { i -> valid[i % valid.size] }
        assertEquals("Maths", scheduleDay0[0])
        assertEquals("Physics", scheduleDay0[1])
        assertEquals("Maths", scheduleDay0[2])
        assertEquals("Physics", scheduleDay0[3])
        assertEquals("Maths", scheduleDay0[4])

        // Regenerate with startIndex = 1
        val scheduleDay1 = Array(5) { i -> valid[(i + 1) % valid.size] }
        assertEquals("Physics", scheduleDay1[0])
        assertEquals("Maths", scheduleDay1[1])
        assertEquals("Physics", scheduleDay1[2])
        assertEquals("Maths", scheduleDay1[3])
        assertEquals("Physics", scheduleDay1[4])
    }

    @Test
    fun timetable_handlesAllEmptySubjects() {
        val entered = arrayOf("", "", "", "", "")
        val valid = entered.filter { it.isNotBlank() }
        val defaultText = if (valid.isEmpty()) "Free / Self Study" else valid[0]
        assertEquals("Free / Self Study", defaultText)
    }
}