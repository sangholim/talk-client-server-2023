package com.talk.client.service.profile

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ProfileController(
        private val profileClient: ProfileClient
) {
    /**
     * 프로필이 없는 경우, 생성 화면으로 렌더링
     */
    @GetMapping("/profiles/friends")
    fun friends(authentication: JwtAuthenticationToken): String {
        val profile = profileClient.get(authentication.token.tokenValue) ?: return "profiles/creation"
        return "profiles/friends"
    }
}