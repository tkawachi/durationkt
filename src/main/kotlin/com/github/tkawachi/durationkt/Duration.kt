package com.github.tkawachi.durationkt

import java.lang.Long.numberOfLeadingZeros
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.*

class Duration(val length: Long, val unit: TimeUnit) {
    fun toNanos(): Long = unit.toNanos(length)
    fun toMicros(): Long = unit.toMicros(length)
    fun toMillis(): Long = unit.toMillis(length)
    fun toSeconds(): Long = unit.toSeconds(length)
    fun toMinutes(): Long = unit.toMinutes(length)
    fun toHours(): Long = unit.toHours(length)
    fun toDays(): Long = unit.toDays(length)
    fun toUnit(u: TimeUnit): Double = toNanos().toDouble() / NANOSECONDS.convert(1, u)

    operator fun unaryMinus(): Duration = Duration(-length, unit)

    operator fun plus(other: Duration): Duration {
        val commonUnit = if (other.unit.convert(1, unit) == 0L) unit else other.unit
        val totalLength = safeAdd(
                commonUnit.convert(length, unit),
                commonUnit.convert(other.length, other.unit)
        )
        return Duration(totalLength, commonUnit)
    }

    operator fun minus(other: Duration): Duration = plus(-other)

    operator fun times(factor: Long): Duration = Duration(safeMul(length, factor), unit)

    operator fun div(divisor: Double): Duration = fromNanos((toNanos() / divisor).toLong())

    operator fun div(divisor: Long): Duration = fromNanos(toNanos() / divisor)

    operator fun compareTo(other: Duration): Int = toNanos().compareTo(other.toNanos())

    operator override fun equals(other: Any?): Boolean =
            when (other) {
                is Duration -> toNanos() == other.toNanos()
                else -> false
            }

    override fun hashCode(): Int {
        var result = length.hashCode()
        result += 31 * result + unit.hashCode()
        return result
    }

    private fun unitString(): String = timeUnitName[unit] + (if (length == 1L) "" else "s")

    override fun toString(): String = "$length ${unitString()}"

    companion object {
        val zero: Duration = Duration(0L, NANOSECONDS)

        private fun safeAdd(a: Long, b: Long): Long {
            if ((b > 0) && (a > Long.MAX_VALUE - b) ||
                    (b < 0) && (a < Long.MIN_VALUE - b)) {
                throw IllegalArgumentException("integer overflow")
            }
            return a + b
        }

        private fun safeMul(_a: Long, _b: Long): Long {
            val a = Math.abs(_a)
            val b = Math.abs(_b)
            if (numberOfLeadingZeros(a) + numberOfLeadingZeros(b) < 64)
                throw IllegalArgumentException("multiplication overflow")
            val product = a * b
            if (product < 0) throw IllegalArgumentException("multiplication overflow")
            return if ((a == _a) xor (b == _b)) -product else product
        }

        private val timeUnitName = hashMapOf(
                NANOSECONDS to "nanosecond",
                MICROSECONDS to "microsecond",
                MILLISECONDS to "millisecond",
                SECONDS to "second",
                MINUTES to "minute",
                HOURS to "hour",
                DAYS to "day"
        )

        private val us_per_ns = 1000L
        private val ms_per_ns = us_per_ns * 1000L
        private val s_per_ns = ms_per_ns * 1000L
        private val min_per_ns = s_per_ns * 60L
        private val h_per_ns = min_per_ns * 60L
        private val d_per_ns = h_per_ns * 24L

        fun fromNanos(nanos: Long): Duration =
                if (nanos % d_per_ns == 0L) Duration(nanos / d_per_ns, DAYS)
                else if (nanos % h_per_ns == 0L) Duration(nanos / h_per_ns, HOURS)
                else if (nanos % min_per_ns == 0L) Duration(nanos / min_per_ns, MINUTES)
                else if (nanos % s_per_ns == 0L) Duration(nanos / s_per_ns, SECONDS)
                else if (nanos % ms_per_ns == 0L) Duration(nanos / ms_per_ns, MILLISECONDS)
                else if (nanos % us_per_ns == 0L) Duration(nanos / us_per_ns, MICROSECONDS)
                else Duration(nanos, NANOSECONDS)
    }
}

val Long.nano: Duration
    get() = Duration(this, NANOSECONDS)
val Long.nanos: Duration
    get() = nano
val Long.nanosecond: Duration
    get() = nano
val Long.nanoseconds: Duration
    get() = nano

val Int.nano: Duration
    get() = toLong().nano
val Int.nanos: Duration
    get() = nano
val Int.nanosecond: Duration
    get() = nano
val Int.nanoseconds: Duration
    get() = nano

val Long.micro: Duration
    get() = Duration(this, MICROSECONDS)
val Long.micros: Duration
    get() = micro
val Long.microseconds: Duration
    get() = micro
val Long.microsecond: Duration
    get() = micro

val Int.micro: Duration
    get() = toLong().micro
val Int.micros: Duration
    get() = micro
val Int.microseconds: Duration
    get() = micro
val Int.microsecond: Duration
    get() = micro

val Long.milli: Duration
    get() = Duration(this, MILLISECONDS)
val Long.millis: Duration
    get() = milli
val Long.millisecond: Duration
    get() = milli
val Long.milliseconds: Duration
    get() = milli

val Int.milli: Duration
    get() = toLong().milli
val Int.millis: Duration
    get() = milli
val Int.millisecond: Duration
    get() = milli
val Int.milliseconds: Duration
    get() = milli

val Long.second: Duration
    get() = Duration(this, SECONDS)
val Long.seconds: Duration
    get() = second

val Int.second: Duration
    get() = toLong().second
val Int.seconds: Duration
    get() = second

val Long.minute: Duration
    get() = Duration(this, MINUTES)
val Long.minutes: Duration
    get() = minute

val Int.minute: Duration
    get() = toLong().minute
val Int.minutes: Duration
    get() = minute

val Long.hour: Duration
    get() = Duration(this, HOURS)
val Long.hours: Duration
    get() = hour

val Int.hour: Duration
    get() = toLong().hour
val Int.hours: Duration
    get() = hour

val Long.day: Duration
    get() = Duration(this, DAYS)
val Long.days: Duration
    get() = day

val Int.day: Duration
    get() = toLong().day
val Int.days: Duration
    get() = day
