package com.talk.client.service.profile

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ProfileController(
        private val profileClient: ProfileClient
) {

    /**
     * 프로필 생성 로직
     * 성공한 경우, 프로필-친구 페이지로 redirect
     */
    @PostMapping("/profiles/creation")
    fun create(authentication: JwtAuthenticationToken, payload: ProfileCreationPayload): String {
        val response = profileClient.create(authentication.token.tokenValue, payload)
        if (!response?.statusCode?.is2xxSuccessful!!) throw Exception("계정 생성 실패하였습니다.")
        return "redirect:/friends"
    }
}