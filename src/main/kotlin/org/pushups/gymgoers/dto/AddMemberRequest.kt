package org.pushups.gymgoers.dto

data class AddMemberRequest(
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val phoneNumber: String? = null
)