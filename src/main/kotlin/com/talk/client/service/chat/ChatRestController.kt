package com.talk.client.service.chat

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatRestController(
        private val chatClient: ChatClient
) {

    @PostMapping(value = ["/api/chats"])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(authentication: JwtAuthenticationToken, @RequestBody payload: ChatCreationPayload) {
        chatClient.create(authentication.token.tokenValue, payload)
    }
}