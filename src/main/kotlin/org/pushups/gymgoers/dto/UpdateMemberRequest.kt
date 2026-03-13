package org.pushups.gymgoers.dto

data class UpdateMemberRequest(
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val phoneNumber: String? = null
)
