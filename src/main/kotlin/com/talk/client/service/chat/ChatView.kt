package com.talk.client.service.chat

import com.talk.client.service.profile.ProfileChatView
import java.util.*

data class ChatView(
        val id: UUID,
        val roomName: String,
        val image: String,
        val participantCount: Int
) {
    companion object {
        fun from(profileChatView: ProfileChatView): ChatView = ChatView(
                id = profileChatView.chatId,
                roomName = profileChatView.roomName,
                image = profileChatView.image,
                participantCount = profileChatView.participantCount
        )
    }
}
