package com.github.tkawachi.durationkt

import org.junit.Assert.*
import org.junit.Test
import java.util.concurrent.TimeUnit.*

class DurationTest {
    @Test
    fun testToNanos() {
        assertEquals(1000000000L, 1.second.toNanos())
    }

    @Test
    fun testToMicros() {
        assertEquals(1000000L, 1.second.toMicros())
    }

    @Test
    fun testToMillis() {
        assertEquals(1000L, 1.second.toMillis())
    }

    @Test
    fun testToSeconds() {
        assertEquals(60L, 1.minute.toSeconds())
    }

    @Test
    fun testToMinutes() {
        assertEquals(60L, 1.hour.toMinutes())
    }

    @Test
    fun testToHours() {
        assertEquals(24L, 1.day.toHours())
    }

    @Test
    fun testToDays() {
        assertEquals(10L, 10.days.toDays())
    }

    @Test
    fun testToUnit() {
        assertEquals(60.0, 1.minute.toUnit(SECONDS), 1e-30)
    }

    @Test
    fun testLongNanoExt() {
        assertEquals(Duration(3L, NANOSECONDS), 3L.nano)
        assertEquals(Duration(3L, NANOSECONDS), 3L.nanos)
        assertEquals(Duration(3L, NANOSECONDS), 3L.nanosecond)
        assertEquals(Duration(3L, NANOSECONDS), 3L.nanoseconds)
    }

    @Test
    fun testIntNanoExt() {
        assertEquals(Duration(3L, NANOSECONDS), 3.nano)
        assertEquals(Duration(3L, NANOSECONDS), 3.nanos)
        assertEquals(Duration(3L, NANOSECONDS), 3.nanosecond)
        assertEquals(Duration(3L, NANOSECONDS), 3.nanoseconds)
    }

    @Test
    fun testLongMicroExt() {
        assertEquals(Duration(3L, MICROSECONDS), 3L.micro)
        assertEquals(Duration(3L, MICROSECONDS), 3L.micros)
        assertEquals(Duration(3L, MICROSECONDS), 3L.microsecond)
        assertEquals(Duration(3L, MICROSECONDS), 3L.microseconds)
    }

    @Test
    fun testIntMicroExt() {
        assertEquals(Duration(3L, MICROSECONDS), 3.micro)
        assertEquals(Duration(3L, MICROSECONDS), 3.micros)
        assertEquals(Duration(3L, MICROSECONDS), 3.microsecond)
        assertEquals(Duration(3L, MICROSECONDS), 3.microseconds)
    }

    @Test
    fun testLongMilliExt() {
        assertEquals(Duration(3L, MILLISECONDS), 3L.milli)
        assertEquals(Duration(3L, MILLISECONDS), 3L.millis)
        assertEquals(Duration(3L, MILLISECONDS), 3L.millisecond)
        assertEquals(Duration(3L, MILLISECONDS), 3L.milliseconds)
    }

    @Test
    fun testIntMilliExt() {
        assertEquals(Duration(3L, MILLISECONDS), 3.milli)
        assertEquals(Duration(3L, MILLISECONDS), 3.millis)
        assertEquals(Duration(3L, MILLISECONDS), 3.millisecond)
        assertEquals(Duration(3L, MILLISECONDS), 3.milliseconds)
    }

    @Test
    fun testLongSecondExt() {
        assertEquals(Duration(3L, SECONDS), 3L.second)
        assertEquals(Duration(3L, SECONDS), 3L.seconds)
    }

    @Test
    fun testIntSecondExt() {
        assertEquals(Duration(3L, SECONDS), 3.second)
        assertEquals(Duration(3L, SECONDS), 3.seconds)
    }

    @Test
    fun testLongMinuteExt() {
        assertEquals(Duration(3L, MINUTES), 3L.minute)
        assertEquals(Duration(3L, MINUTES), 3L.minutes)
    }

    @Test
    fun testIntMinuteExt() {
        assertEquals(Duration(3L, MINUTES), 3.minute)
        assertEquals(Duration(3L, MINUTES), 3.minutes)
    }

    @Test
    fun testLongHourExt() {
        assertEquals(Duration(3L, HOURS), 3L.hour)
        assertEquals(Duration(3L, HOURS), 3L.hours)
    }

    @Test
    fun testIntHourExt() {
        assertEquals(Duration(3L, HOURS), 3.hour)
        assertEquals(Duration(3L, HOURS), 3.hours)
    }

    @Test
    fun testLongDayExt() {
        assertEquals(Duration(3L, DAYS), 3L.day)
        assertEquals(Duration(3L, DAYS), 3L.days)
    }

    @Test
    fun testIntDayExt() {
        assertEquals(Duration(3L, DAYS), 3.day)
        assertEquals(Duration(3L, DAYS), 3.days)
    }

    @Test
    fun testEquals() {
        assertEquals(2.minutes, 120000.millis)
    }

    @Test
    fun testPlus() {
        assertEquals(3.hours, 2.hours + 1.hour)
        assertEquals(60001.millis, 1.milli + 1.minute)

        val x = 1234567890L
        assertEquals(Long.MAX_VALUE.millis, x.millis + (Long.MAX_VALUE - x).millis)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testPlusOverflow() {
        val x = 1234567890L
        assertEquals(Long.MAX_VALUE.millis, x.millis + (Long.MAX_VALUE - x + 1).millis)
    }

    @Test
    fun testMinus() {
        assertEquals(1.hour, 3.hours - 2.hours)
        assertEquals(59.seconds, 1.minute - 1.second)
    }

    @Test
    fun testTimes() {
        val d = 20.seconds * 3
        assertEquals(60, d.length)
        assertEquals(SECONDS, d.unit)
    }

    @Test
    fun testUnaryMinus() {
        assertEquals(Duration(-3L, SECONDS), -3.seconds)
    }

    @Test
    fun testCompareTo() {
        assertTrue(2.seconds < 3.seconds)
        assertFalse(1.minute > 60.seconds)
        assertTrue(1.minute >= 60.seconds)
    }
}
