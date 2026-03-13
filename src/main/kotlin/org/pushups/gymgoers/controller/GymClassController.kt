package org.pushups.gymgoers.controller

import org.pushups.gymgoers.dto.GymClassDto
import org.pushups.gymgoers.service.GymClassService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.DayOfWeek
import java.time.LocalDate

@RestController
@RequestMapping("/api/class")
class GymClassController(
    private val gymClassService: GymClassService
){
    @GetMapping
    fun findAll(): List<GymClassDto> = gymClassService.findAll()

    @GetMapping("/daily")
    fun findClassesByDay(
        @RequestParam dayOfWeek: DayOfWeek
    ): List<GymClassDto> = gymClassService.findClassesByDay(dayOfWeek = dayOfWeek)
}