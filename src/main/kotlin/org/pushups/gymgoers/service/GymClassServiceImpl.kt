package org.pushups.gymgoers.service

import org.pushups.gymgoers.dto.GymClassDto
import org.pushups.gymgoers.dto.toDto
import org.pushups.gymgoers.repository.GymClassRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class GymClassServiceImpl(
    private val repository: GymClassRepository
): GymClassService {
    override fun findAll(): List<GymClassDto> =
        repository.findAll().map { it.toDto() }

    override fun findClassesByDay(@Param("dayOfWeek") dayOfWeek: DayOfWeek): List<GymClassDto> =
        repository.findByDayOfTheWeek(dayOfWeek).map { it.toDto() }
}