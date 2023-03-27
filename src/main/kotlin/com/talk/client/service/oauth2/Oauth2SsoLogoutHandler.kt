package com.talk.client.service.oauth2

import com.talk.client.service.util.CookieUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.web.authentication.logout.LogoutHandler

class Oauth2SsoLogoutHandler(
        private val oauth2Service: Oauth2Service,
        private val oAuth2AuthorizedClientRepository: OAuth2AuthorizedClientRepository
) : LogoutHandler {

    private val clientRegistrationId = "sso"

    override fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
        val authorizedClient: OAuth2AuthorizedClient = oAuth2AuthorizedClientRepository.loadAuthorizedClient(clientRegistrationId, authentication, request)
                ?: return
        oauth2Service.logout(authorizedClient)
        CookieUtils.deleteCookie(request, response, Oauth2Constant.ACCESS_TOKEN)
        CookieUtils.deleteCookie(request, response, Oauth2Constant.REFRESH_TOKEN)
    }
}