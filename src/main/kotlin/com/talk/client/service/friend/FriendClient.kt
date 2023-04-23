package com.talk.client.service.friend

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class FriendClient(
        private val resourceClient: WebClient
) {
    fun create(token: String, payload: FriendCreationPayload): FriendView? = resourceClient.post().uri("/member-service/friends")
            .headers {
                it.setBearerAuth(token)
            }
            .bodyValue(payload)
            .retrieve()
            .bodyToMono<FriendView>()
            .block()
}