package com.talk.client.service.friend

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class FriendController {

    @GetMapping("/friends")
    fun getAll(authentication: Authentication) {
        println("test: $authentication")
    }
}