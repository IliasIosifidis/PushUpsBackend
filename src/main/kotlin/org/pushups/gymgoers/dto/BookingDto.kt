package org.pushups.gymgoers.dto

import org.pushups.gymgoers.model.GymClass
import org.pushups.gymgoers.model.Member
import java.time.LocalDate
import java.time.LocalDateTime

data class BookingDto(
    val id: Long? = null,
    val member: Member,
    val gymClass: GymClass,
    val date: LocalDate,
    val bookedAt: LocalDateTime
)