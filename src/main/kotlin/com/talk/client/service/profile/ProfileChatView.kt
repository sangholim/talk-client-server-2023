package com.talk.client.service.profile

import java.util.*

data class ProfileChatView(
        val id: UUID,
        val chatId: UUID,
        val roomName: String,
        val image: String,
        val participantCount: Int
)