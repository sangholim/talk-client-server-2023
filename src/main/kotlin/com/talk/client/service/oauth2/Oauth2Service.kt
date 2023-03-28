package com.talk.client.service.oauth2

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient

@Service
class Oauth2Service(
        private val oauth2Client: WebClient,
        private val objectMapper: ObjectMapper
) {

    fun revokeToken(providerDetails: ProviderDetails, payload: Oauth2TokenRevokePayload) {
        val uri = providerDetails.configurationMetadata["revocation_endpoint"] ?: return
        oauth2Client.revokeToken(uri.toString(), payload)
    }

    private fun WebClient.revokeToken(uri: String, payload: Oauth2TokenRevokePayload) {
        val map = objectMapper.convertValue<Map<String, String>>(payload)
        val multiValueMap = LinkedMultiValueMap<String, Any?>().apply {
            setAll(map)
        }
        this.post().uri(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(multiValueMap)
                .retrieve()
                .toBodilessEntity()
                .subscribe()
    }
}