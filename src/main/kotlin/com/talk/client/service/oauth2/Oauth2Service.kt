package com.talk.client.service.oauth2

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient

@Service
class Oauth2Service(
        private val oauth2Client: WebClient,
        private val objectMapper: ObjectMapper
) {

    fun logout(authorizedClient: OAuth2AuthorizedClient) {
        val uri = authorizedClient.clientRegistration.providerDetails.configurationMetadata["revocation_endpoint"] ?: return
        val accessToken = authorizedClient.accessToken.tokenValue
        val refreshToken = authorizedClient.refreshToken?.tokenValue
        listOfNotNull(
                Oauth2TokenRevokePayload.accessTokenOf(accessToken, clientRegistration = authorizedClient.clientRegistration),
                Oauth2TokenRevokePayload.refreshTokenOf(refreshToken, clientRegistration = authorizedClient.clientRegistration)
        ).forEach {
            oauth2Client.revokeToken(uri.toString(), it)
        }
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