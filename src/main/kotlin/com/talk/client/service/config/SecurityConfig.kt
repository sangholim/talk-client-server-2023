package com.talk.client.service.config

import com.talk.client.service.oauth2.HttpCookieOauth2AuthorizationRequestRepository
import com.talk.client.service.oauth2.Oauth2AuthenticationEntryPoint
import com.talk.client.service.oauth2.Oauth2Constant
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val httpCookieOauth2AuthorizationRequestRepository: HttpCookieOauth2AuthorizationRequestRepository,
        private val authenticationEntryPoint: Oauth2AuthenticationEntryPoint
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.oauth2Client { oauth2Client ->
            oauth2Client.authorizationCodeGrant().authorizationRequestRepository(httpCookieOauth2AuthorizationRequestRepository)
        }
        http.formLogin().disable()
        http.authorizeHttpRequests {
            it.requestMatchers("/", "/oauth2/login").permitAll()
            it.anyRequest().authenticated()
        }
        http.oauth2ResourceServer().jwt()
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)

        http.logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies(Oauth2Constant.ACCESS_TOKEN, Oauth2Constant.REFRESH_TOKEN, "JSESSIONID")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        return http.build()
    }
}