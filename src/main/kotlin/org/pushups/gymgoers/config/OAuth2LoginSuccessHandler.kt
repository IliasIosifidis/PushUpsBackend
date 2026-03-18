package org.pushups.gymgoers.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.pushups.gymgoers.model.Member
import org.pushups.gymgoers.repository.BookingRepository
import org.pushups.gymgoers.repository.MemberRepository
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val memberRepository: MemberRepository
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oauth2User = authentication.principal as OAuth2User
        val email = oauth2User.attributes["email"] as String
        val firstName = oauth2User.attributes["given_name"] as String? ?: ""
        val lastName = oauth2User.attributes["family_name"] as String? ?: ""

        // Find or create a member
        val member = memberRepository.findByEmail(email)
            ?: memberRepository.save(
                Member(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    role = "MEMBER"
                )
            )

        //Send to the frontend after login
//        response.sendRedirect("http://localhost:3000")  // LOCAL
        response.sendRedirect("pushupsfrontend.up.railway.app") // ONLINE
    }
}