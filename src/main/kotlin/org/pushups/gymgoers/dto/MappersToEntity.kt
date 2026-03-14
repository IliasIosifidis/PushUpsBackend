package org.pushups.gymgoers.dto

import org.pushups.gymgoers.model.Booking
import org.pushups.gymgoers.model.GymClass
import org.pushups.gymgoers.model.Member

fun MemberDto.toEntity(): Member{
    return Member(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber
    )
}

fun BookingDto.toEntity(): Booking{
    return Booking(
        id = id,
        member = member.toEntity(),
        gymClass = gymClass.toEntity(),
        date = date,
        bookedAt = bookedAt
    )
}

fun GymClassDto.toEntity(): GymClass{
    return GymClass(
        id = id,
        name = name,
        dayOfTheWeek = dayOfTheWeek,
        startTime = startTime,
        endTime = endTime,
        maxCapacity = maxCapacity
    )
}