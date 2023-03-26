package com.talk.client.service.oauth2

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.oauth2.client.registration.ClientRegistration

data class Oauth2TokenRevokePayload(
        val token: String,

        @field:JsonProperty(value = "token_type_hint")
        val tokenTypeHint: String,

        @field:JsonProperty(value = "client_id")
        val clientId: String,

        @field:JsonProperty(value = "client_secret")
        val clientSecret: String
) {
    companion object {
        fun accessTokenOf(token: String, clientRegistration: ClientRegistration): Oauth2TokenRevokePayload =
                Oauth2TokenRevokePayload(
                        token = token,
                        tokenTypeHint = Oauth2Constant.ACCESS_TOKEN,
                        clientId = clientRegistration.clientId,
                        clientSecret = clientRegistration.clientSecret
                )

        fun refreshTokenOf(token: String?, clientRegistration: ClientRegistration): Oauth2TokenRevokePayload? {
            if (token == null) return null
            return Oauth2TokenRevokePayload(
                    token = token,
                    tokenTypeHint = Oauth2Constant.ACCESS_TOKEN,
                    clientId = clientRegistration.clientId,
                    clientSecret = clientRegistration.clientSecret
            )
        }
    }
}
