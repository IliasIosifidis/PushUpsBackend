package org.pushups.gymgoers.repository

import org.pushups.gymgoers.model.Booking
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface BookingRepository : JpaRepository<Booking, Long> {

    @Query("SELECT COUNT(b), g.name FROM Booking b JOIN b.gymClass g WHERE b.date = :date GROUP BY g.name")
    fun countBookingsByDate(@Param("date") date: LocalDate): List<Array<Any>>

    fun findByMemberId(memberId: Long, pageable: Pageable): Page<Booking>

    fun countByGymClassIdAndDate(gymClassId: Long?, date: LocalDate): Long
}