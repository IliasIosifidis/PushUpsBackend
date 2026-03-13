package org.pushups.gymgoers.dto

import java.time.DayOfWeek
import java.time.LocalTime

data class GymClassDto(
    val id: Long? = null,
    val name: String = "",
    val dayOfTheWeek: DayOfWeek,
    val startTime: LocalTime = LocalTime.of(0,0),
    val endTime: LocalTime = LocalTime.of(0,0),
    val maxCapacity: Int = 20
)