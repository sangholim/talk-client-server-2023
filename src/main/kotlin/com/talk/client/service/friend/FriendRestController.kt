package com.talk.client.service.friend

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FriendRestController {

    @PostMapping(value = ["/friends"])
    fun insert(authentication: JwtAuthenticationToken, @RequestBody payload: FriendCreationPayload) {
    }
}

