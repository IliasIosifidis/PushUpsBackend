package org.pushups.gymgoers.service

import org.pushups.gymgoers.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Component

@Component
class CustomOidcUserService(
    private val memberRepository: MemberRepository
) : OidcUserService() {
    override fun loadUser(userRequest: OidcUserRequest?): OidcUser {
        val oidcUser = super.loadUser(userRequest)
        val email = oidcUser.attributes["email"] as String

        val member = memberRepository.findByEmail(email)
        val role = member?.role ?: "MEMBER"

        val authorities = oidcUser.authorities.toMutableList()
        authorities.add(SimpleGrantedAuthority("ROLE_$role"))

        return DefaultOidcUser(authorities, oidcUser.idToken, oidcUser.userInfo)
    }
}
