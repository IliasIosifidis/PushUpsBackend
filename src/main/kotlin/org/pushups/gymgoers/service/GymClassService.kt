package org.pushups.gymgoers.service

import org.pushups.gymgoers.dto.GymClassDto
import java.time.DayOfWeek
import java.time.LocalDate

interface GymClassService {
    fun findAll(): List<GymClassDto>
    fun findClassesByDay(dayOfWeek: DayOfWeek): List<GymClassDto>
}