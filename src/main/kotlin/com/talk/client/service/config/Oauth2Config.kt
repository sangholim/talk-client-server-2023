package com.talk.client.service.config

import com.talk.client.service.oauth2.HttpCookieOauth2AuthorizationRequestRepository
import com.talk.client.service.oauth2.Oauth2AuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Oauth2Config {

    @Bean
    fun authorizedClientManager(
            clientRegistrationRepository: ClientRegistrationRepository,
            authorizedClientRepository: OAuth2AuthorizedClientRepository): OAuth2AuthorizedClientManager {
        val authorizedClientProvider: OAuth2AuthorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .refreshToken()
                .clientCredentials()
                .build()
        val authorizedClientManager = DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository)
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)
        return authorizedClientManager
    }

    @Bean
    fun webClient(authorizedClientManager: OAuth2AuthorizedClientManager): WebClient {
        val oauth2Client = ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager)
        return WebClient.builder()
                .apply(oauth2Client.oauth2Configuration())
                .build()
    }

    @Bean
    fun httpCookieOauth2AuthorizationRequestRepository(): HttpCookieOauth2AuthorizationRequestRepository =
            HttpCookieOauth2AuthorizationRequestRepository()

    @Bean
    fun authenticationEntryPoint(): Oauth2AuthenticationEntryPoint =
            Oauth2AuthenticationEntryPoint()
}