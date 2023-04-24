package com.talk.client.service.friend

import com.talk.client.service.profile.ProfileClient
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class FriendController(
        private val profileClient: ProfileClient
) {

    /**
     * 자신의 프로필, 친구의 프로필들을 UI 제공
     * 프로필이 없는 경우, 생성 화면으로 렌더링
     */
    @GetMapping("/friends")
    fun friends(authentication: JwtAuthenticationToken, model: Model): String {
        val profile = profileClient.get(authentication.token.tokenValue)
        if (profile == null) {
            model.addAttribute("email", authentication.tokenAttributes["email"])
            return "profile/creation"
        }
        model.addAttribute("profile", profile)
        return "friend/main"
    }
}