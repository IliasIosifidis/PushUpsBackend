package org.pushups.gymgoers.service

import org.pushups.gymgoers.dto.BookingDto
import org.pushups.gymgoers.dto.BookingRequest
import org.pushups.gymgoers.dto.toDto
import org.pushups.gymgoers.exception.ClassFullException
import org.pushups.gymgoers.exception.ResourceNotFoundException
import org.pushups.gymgoers.model.Booking
import org.pushups.gymgoers.repository.BookingRepository
import org.pushups.gymgoers.repository.GymClassRepository
import org.pushups.gymgoers.repository.MemberRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class BookingServiceImpl(
    private val bookingRepository: BookingRepository,
    private val gymClassRepository: GymClassRepository,
    private val memberRepository: MemberRepository
) : BookingService {
    override fun findAll(pageable: Pageable): Page<BookingDto> =
        bookingRepository.findAll(pageable).map { it.toDto() }

    override fun countBookingsByDate(date: LocalDate): List<Array<Any>> =
        bookingRepository.countBookingsByDate(date)

    override fun addBooking(req: BookingRequest): BookingDto {
        // CRATING VALUES
        val member = memberRepository.findById(req.memberId)
            .orElseThrow { ResourceNotFoundException(" There is no member with this id: ${req.memberId}") }
        val gymClass = gymClassRepository.findById(req.gymClassId)
            .orElseThrow { ResourceNotFoundException("Class not found") }
        val date = req.date
        // EXCEPTION WHEN CLASS IS FULL
        val currentBookings = bookingRepository.countByGymClassIdAndDate(gymClassId = gymClass.id, date =  date)
        if (currentBookings >= gymClass.maxCapacity){
            throw ClassFullException("Class ${gymClass.name} is full at ${req.date}")
        }
        // CRATING THE BOOKING
        val booking = Booking(
            member = member,
            gymClass = gymClass,
            date = date,
            bookedAt = LocalDateTime.now()
        )
        return bookingRepository.save(booking).toDto()
    }

    override fun deleteBooking(id: Long) {
        if (!bookingRepository.existsById(id)){
            throw ResourceNotFoundException("There is no booking with this ID: $id")
        }
        bookingRepository.deleteById(id)
    }

    override fun findMemberBookings(memberId: Long,pageable: Pageable): Page<BookingDto> {
        if (!memberRepository.existsById(memberId)){
            throw ResourceNotFoundException("Member with id: $memberId does not exist")
        }
        return bookingRepository.findByMemberId(memberId, pageable).map { it.toDto() }
    }
}