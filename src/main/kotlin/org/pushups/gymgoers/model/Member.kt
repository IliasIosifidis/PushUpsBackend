package org.pushups.gymgoers.model

import jakarta.persistence.*

@Entity
@Table(name = "member")
data class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "first_name", length = 50, nullable = false)
    var firstName: String = "",

    @Column(name = "last_name", length = 50, nullable = false)
    var lastName: String = "",

    @Column(unique = true)
    var email: String? = null,

    @Column(unique = true)
    var phoneNumber: String? = null,

    @Column(nullable = false, columnDefinition = "tinyint default '1'")
    var active: Boolean = true,

    @Column(nullable = false)
    var role: String = "MEMBER",

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var bookings: MutableList<Booking> = mutableListOf()
)