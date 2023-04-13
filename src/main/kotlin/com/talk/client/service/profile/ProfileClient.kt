package com.talk.client.service.profile

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

@Component
class ProfileClient(
        private val resourceClient: WebClient
) {
    fun create(token: String, payload: ProfileCreationPayload) = resourceClient.post().uri("/member-service/profiles")
            .headers {
                it.setBearerAuth(token)
            }
            .bodyValue(payload)
            .retrieve()
            .toBodilessEntity()
            .block()

    fun get(token: String): ProfileView? = resourceClient.get().uri("/member-service/profiles")
            .headers {
                it.setBearerAuth(token)
            }
            .retrieve()
            .bodyToMono(ProfileView::class.java)
            .onErrorResume(WebClientResponseException::class.java) {
                if (it.statusCode.value() == HttpStatus.NOT_FOUND.value()) Mono.empty() else Mono.error(it)
            }.block()
}