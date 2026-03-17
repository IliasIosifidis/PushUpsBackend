package org.pushups.gymgoers.controller

import org.pushups.gymgoers.repository.MemberRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val memberRepository: MemberRepository
) {

    @GetMapping("/me")
    fun getCurrentUser(@AuthenticationPrincipal user: OAuth2User?): ResponseEntity<Any> {
        if (user == null) {
            return ResponseEntity.status(401).body(mapOf("authenticated" to false))
        }

        val email = user.attributes["email"] as String
        val member = memberRepository.findByEmail(email)

        return ResponseEntity.ok(
            mapOf(
                "authenticated" to true,
                "id" to member?.id,
                "firstName" to member?.firstName,
                "lastName" to member?.lastName,
                "email" to member?.email,
                "role" to member?.role
            )
        )
    }
}