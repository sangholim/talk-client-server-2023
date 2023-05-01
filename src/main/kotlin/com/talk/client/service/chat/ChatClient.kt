package com.talk.client.service.chat

import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class ChatClient(
        private val resourceClient: WebClient
) {

    fun getAll(token: String) = resourceClient.get()
            .uri("/member-service/profile/chats")
            .headers {
                it.setBearerAuth(token)
            }.retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<ChatView>>() {})
            .block()

    fun create(token: String, payload: ChatCreationPayload) = resourceClient.post()
            .uri("/member-service/profile/chats")
            .headers {
                it.setBearerAuth(token)
            }.bodyValue(payload).retrieve()
            .toBodilessEntity().block()
}
