package org.pushups.gymgoers.model

import jakarta.persistence.*
import java.time.DayOfWeek
import java.time.LocalTime

@Entity
@Table(name = "gym_class")
data class GymClass(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var dayOfTheWeek: DayOfWeek,

    @Column(nullable = false)
    var startTime: LocalTime = LocalTime.of(0,0),

    @Column(nullable = false)
    var endTime: LocalTime = LocalTime.of(0,0),

    @Column(nullable = false)
    var maxCapacity: Int = 20,
)