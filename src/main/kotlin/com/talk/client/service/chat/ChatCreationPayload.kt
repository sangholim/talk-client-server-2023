package com.talk.client.service.chat

/**
 * 채팅방 생성 요청 필드 데이터
 */
data class ChatCreationPayload (
        /**
         * 친구 ID 리스트
         */
        val friendIds: List<String>
)
