package org.pushups.gymgoers.dto

import java.time.LocalDate

data class BookingRequest(
    val memberId: Long,
    val gymClassId: Long,
    val date: LocalDate
)