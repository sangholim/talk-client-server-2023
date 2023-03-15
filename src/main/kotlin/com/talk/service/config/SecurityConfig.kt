package com.talk.service.config

import com.talk.service.oauth2.HttpCookieOauth2AuthorizationRequestRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val httpCookieOauth2AuthorizationRequestRepository: HttpCookieOauth2AuthorizationRequestRepository
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http.oauth2Client { oauth2Client ->
            oauth2Client.authorizationCodeGrant().authorizationRequestRepository(httpCookieOauth2AuthorizationRequestRepository)
        }

        return http.build()
    }
}