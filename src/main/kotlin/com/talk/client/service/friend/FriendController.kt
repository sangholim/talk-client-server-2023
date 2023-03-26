package com.talk.client.service.friend

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class FriendController {

    /**
     * 친구 리스트 화면 렌더링
     * 기본 로그인후 이동하는 페이지
     */
    @GetMapping("/friends")
    fun getAll(authentication: Authentication): String {
        return "friends/main"
    }
}