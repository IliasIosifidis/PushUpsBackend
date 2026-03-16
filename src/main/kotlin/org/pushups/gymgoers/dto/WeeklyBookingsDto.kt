package org.pushups.gymgoers.dto

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class BookingMemberDto(
    val bookingId: Long?,
    val id: Long?,
    val firstName: String,
    val lastName: String
)

data class DailyClassDto(
    val classId: Long?,
    val className: String,
    val dayOfTheWeek: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val members: List<BookingMemberDto>
)

data class WeeklyBookingsDto(
    val weekStart: LocalDate,
    val weekEnd: LocalDate,
    val days: Map<LocalDate, List<DailyClassDto>>
)