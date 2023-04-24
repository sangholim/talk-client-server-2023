package com.talk.client.service.chat

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ChatController {

    @GetMapping(value = ["/chats"])
    fun chatsView(): String {
        return "/chat/main"
    }
}