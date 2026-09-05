package com.example.mad_assignment

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun subject_dataClass_holdsValues() {
        val subject = Subject(
            name = "Android Development",
            teacher = "Prof. Patel",
            day = "Monday",
            startTime = "09:00 AM",
            endTime = "10:00 AM",
            room = "Lab-1"
        )

        assertEquals("Android Development", subject.name)
        assertEquals("Prof. Patel", subject.teacher)
        assertEquals("Monday", subject.day)
        assertEquals("09:00 AM", subject.startTime)
        assertEquals("10:00 AM", subject.endTime)
        assertEquals("Lab-1", subject.room)
    }

    @Test
    fun standardSlots_areDefinedCorrectly() {
        val slots = SubjectRepository.STANDARD_SLOTS
        assertTrue(slots.isNotEmpty())
        assertEquals(5, slots.size)
        assertEquals("09:00 AM", slots[0].start)
        assertEquals("10:00 AM", slots[0].end)
    }

    @Test
    fun weekdays_containStandardDays() {
        val weekdays = SubjectRepository.WEEKDAYS
        assertTrue(weekdays.contains("Monday"))
        assertTrue(weekdays.contains("Friday"))
        assertEquals(6, weekdays.size)
    }
}