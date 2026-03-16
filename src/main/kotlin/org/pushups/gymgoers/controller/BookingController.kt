package org.pushups.gymgoers.controller

import org.pushups.gymgoers.dto.BookingDto
import org.pushups.gymgoers.dto.BookingRequest
import org.pushups.gymgoers.dto.WeeklyBookingsDto
import org.pushups.gymgoers.service.BookingService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/booking")
class BookingController(
    private val service: BookingService
) {

    @GetMapping()
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): Page<BookingDto> {
        return service.findAll(PageRequest.of(page,size))
    }

    @GetMapping("/daily")
    fun countBookingsByDate(
        @RequestParam date: LocalDate
    ): List<Array<Any>> = service.countBookingsByDate(date)

    @GetMapping("/weekly")
    fun getWeeklyBookings(
        @RequestParam date: LocalDate
    ): WeeklyBookingsDto = service.getWeeklyClasses(date)

    @GetMapping("/member/{id}")
    fun findMemberBookings(
        @PathVariable("id") id: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "25") size: Int
    ): Page<BookingDto> = service.findMemberBookings(memberId = id, PageRequest.of(page,size) )

    @PostMapping
    fun addBooking(
        @RequestBody() bookingRequest: BookingRequest
    ): BookingDto = service.addBooking(bookingRequest)

    @DeleteMapping("/{id}")
    fun deleteBooking(
        @PathVariable("id") id: Long
    ): ResponseEntity<Void> {
        service.deleteBooking(id)
        return ResponseEntity.noContent().build()
    }
}