package com.talk.client.service.oauth2

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.oauth2.client.registration.ClientRegistration

data class Oauth2TokenRevokePayload(
        val token: String,

        @field:JsonProperty(value = "client_id")
        val clientId: String,

        @field:JsonProperty(value = "client_secret")
        val clientSecret: String
) {
    companion object {
        fun of(token: String, clientRegistration: ClientRegistration): Oauth2TokenRevokePayload =
                Oauth2TokenRevokePayload(
                        token = token,
                        clientId = clientRegistration.clientId,
                        clientSecret = clientRegistration.clientSecret
                )
    }
}
