package org.pushups.gymgoers.service

import org.pushups.gymgoers.dto.BookingDto
import org.pushups.gymgoers.dto.BookingRequest
import org.pushups.gymgoers.dto.WeeklyBookingsDto
import org.pushups.gymgoers.model.Booking
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface BookingService {
    fun findAll(pageable: Pageable): Page<BookingDto>
    fun countBookingsByDate(date: LocalDate): List<Array<Any>>
    fun addBooking(req: BookingRequest): BookingDto
    fun deleteBooking(id: Long)
    fun findMemberBookings(memberId: Long, pageable: Pageable): Page<BookingDto>
    fun getWeeklyClasses(date: LocalDate): WeeklyBookingsDto
    fun memberStats(memberId: Long): List<LocalDate>
}