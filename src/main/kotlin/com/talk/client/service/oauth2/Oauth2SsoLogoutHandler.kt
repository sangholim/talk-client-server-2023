package com.talk.client.service.oauth2

import com.talk.client.service.util.CookieUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.authentication.logout.LogoutHandler

class Oauth2SsoLogoutHandler(
        private val oauth2Service: Oauth2Service,
        private val clientRegistrationRepository: ClientRegistrationRepository
) : LogoutHandler {

    private val clientRegistrationId = "sso"

    override fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
        val clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId) ?: return
        val cookies = listOfNotNull(CookieUtils.getCookie(request, Oauth2Constant.ACCESS_TOKEN), CookieUtils.getCookie(request, Oauth2Constant.REFRESH_TOKEN))
        cookies.forEach {
            val payload = Oauth2TokenRevokePayload.of(it.value, clientRegistration)
            oauth2Service.revokeToken(clientRegistration.providerDetails, payload)
            // 쿠키 제거
            CookieUtils.deleteCookie(request, response, it.name)
        }
    }
}