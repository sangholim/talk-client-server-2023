package com.talk.service.config

import com.talk.service.oauth2.HttpCookieOAuth2AuthorizationRequestRepository
import com.talk.service.oauth2.OAuth2AuthenticationFailureHandler
import com.talk.service.oauth2.OAuth2AuthenticationSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Oauth2Config {

    @Bean
    fun httpCookieOAuth2AuthorizationRequestRepository(): HttpCookieOAuth2AuthorizationRequestRepository =
            HttpCookieOAuth2AuthorizationRequestRepository()


    @Bean
    fun oAuth2AuthenticationFailureHandler(httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository): OAuth2AuthenticationFailureHandler =
            OAuth2AuthenticationFailureHandler(httpCookieOAuth2AuthorizationRequestRepository)

    @Bean
    fun oAuth2AuthenticationSuccessHandler(httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository): OAuth2AuthenticationSuccessHandler =
            OAuth2AuthenticationSuccessHandler(httpCookieOAuth2AuthorizationRequestRepository)

}