package com.talk.client.service.friend

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class FriendRestController(
        private val client: FriendClient
) {
    /**
     * resource 서버로 친구 생성 요청
     */
    @PostMapping(value = ["/api/friends"])
    @ResponseStatus(HttpStatus.CREATED)
    fun insert(authentication: JwtAuthenticationToken, @RequestBody payload: FriendCreationPayload): FriendView? {
        return client.create(authentication.token.tokenValue, payload)
    }
}

