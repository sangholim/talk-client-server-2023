package com.talk.client.service.profile

import java.util.*

data class ProfileChatDetailView(
        val id: UUID,
        val chatId: UUID,
        val chatParticipantId: UUID,
        val roomName: String,
        val image: String,
        val participantCount: Int
)