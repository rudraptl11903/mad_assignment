package com.example.mad_assignment

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun subject_modelCreation_isCorrect() {
        val subject = Subject(
            name = "Android Development",
            teacher = "Prof. Patel",
            day = "Monday",
            startTime = "09:00 AM",
            endTime = "10:00 AM",
            room = "A-101"
        )

        assertEquals("Android Development", subject.name)
        assertEquals("Prof. Patel", subject.teacher)
        assertEquals("Monday", subject.day)
        assertEquals("09:00 AM", subject.startTime)
        assertEquals("10:00 AM", subject.endTime)
        assertEquals("A-101", subject.room)
    }

    @Test
    fun subject_daysList_containsAllWorkingDays() {
        val days = SubjectRepository.DAYS
        assertEquals(6, days.size)
        assertEquals("Monday", days[0])
        assertEquals("Tuesday", days[1])
        assertEquals("Wednesday", days[2])
        assertEquals("Thursday", days[3])
        assertEquals("Friday", days[4])
        assertEquals("Saturday", days[5])
    }

    @Test
    fun dayWiseGrouping_handlesEmptyAndNonEmptyDays() {
        val subjects = listOf(
            Subject("Android Development", "Prof. Patel", "Monday", "09:00 AM", "10:00 AM", "A-101"),
            Subject("Database Management", "Prof. Shah", "Tuesday", "10:00 AM", "11:00 AM", "B-202")
        )

        val mondaySubjects = subjects.filter { it.day.equals("Monday", ignoreCase = true) }
        val wednesdaySubjects = subjects.filter { it.day.equals("Wednesday", ignoreCase = true) }

        assertEquals(1, mondaySubjects.size)
        assertEquals("Android Development", mondaySubjects[0].name)
        assertEquals("Prof. Patel", mondaySubjects[0].teacher)
        assertEquals("A-101", mondaySubjects[0].room)

        assertTrue(wednesdaySubjects.isEmpty())
    }

    @Test
    fun subject_dataClassEquality_isCorrect() {
        val s1 = Subject("Math", "Dr. Rao", "Monday", "09:00 AM", "10:00 AM", "101")
        val s2 = Subject("Math", "Dr. Rao", "Monday", "09:00 AM", "10:00 AM", "101")
        val s3 = Subject("Physics", "Dr. Rao", "Monday", "09:00 AM", "10:00 AM", "101")

        assertEquals(s1, s2)
        assertNotEquals(s1, s3)
    }
}