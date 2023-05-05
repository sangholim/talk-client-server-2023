package com.talk.client.service.chat

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ChatController(
        private val chatClient: ChatClient
) {

    @GetMapping(value = ["/chats"])
    fun getViews(authentication: JwtAuthenticationToken, model: Model): String {
        val response = chatClient.getAll(authentication.token.tokenValue)
        model.addAttribute("chats", response)
        return "/chat/main"
    }


    @GetMapping(value = ["/chats/{id}"])
    fun getRoomView(@PathVariable id: String, authentication: JwtAuthenticationToken, model: Model): String {
        return "/chatRoom/main"
    }
}