package com.talk.client.service.friend

/**
 * 친구 생성 요청 필드 데이터
 */
data class FriendCreationPayload(
        /**
         * 추가 방식 (이메일, 이름)
         */
        val type: FriendRegisterType,

        /**
         * 이메일
         */
        val email: String?,

        /**
         * 이름
         */
        val name: String?
)
