package org.pushups.gymgoers.repository

import org.pushups.gymgoers.model.GymClass
import org.springframework.data.jpa.repository.JpaRepository
import java.time.DayOfWeek

interface GymClassRepository : JpaRepository<GymClass, Long> {
    fun findByDayOfTheWeek(day: DayOfWeek): List<GymClass>
}