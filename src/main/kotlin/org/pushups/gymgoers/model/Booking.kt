package org.pushups.gymgoers.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "booking")
data class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    var member: Member,

    @ManyToOne
    @JoinColumn(name = "gym_class_id", nullable = false)
    var gymClass: GymClass,

    @Column(nullable = false)
    var date: LocalDate = LocalDate.now(),

    @Column(nullable = false)
    var bookedAt: LocalDateTime = LocalDateTime.now(),
)