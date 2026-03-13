package org.pushups.gymgoers.dto

import org.pushups.gymgoers.model.Booking
import org.pushups.gymgoers.model.GymClass
import org.pushups.gymgoers.model.Member

fun Booking.toDto(): BookingDto {
    return BookingDto(
        id = id,
        member = member,
        gymClass = gymClass,
        date = date,
        bookedAt = bookedAt
    )
}

fun GymClass.toDto(): GymClassDto {
    return GymClassDto(
        id = id,
        name = name,
        dayOfTheWeek = dayOfTheWeek,
        startTime = startTime,
        endTime = endTime,
        maxCapacity = maxCapacity
    )
}

fun Member.toDto(): MemberDto {
    return MemberDto(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber
    )
}