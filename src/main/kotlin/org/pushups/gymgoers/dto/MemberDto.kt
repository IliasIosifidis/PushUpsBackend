package org.pushups.gymgoers.dto

data class MemberDto(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val phoneNumber: String? = null,
    val active: Boolean = true
)
