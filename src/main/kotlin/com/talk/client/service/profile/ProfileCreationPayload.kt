package com.talk.client.service.profile

/**
 * 프로필 생성 요청 데이터
 */
data class ProfileCreationPayload(
        val email: String,
        val name: String
)
