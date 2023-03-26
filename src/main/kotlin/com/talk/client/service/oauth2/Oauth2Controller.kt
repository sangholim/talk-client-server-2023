package com.talk.client.service.oauth2

import com.talk.client.service.util.CookieUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class Oauth2Controller(
        private val oauth2Service: Oauth2Service
) {

    /**
     * oauth2 인증을 성공한 경우, 인증이 필요한 기본 화면으로 진입
     */
    @GetMapping("/oauth2/login")
    fun oauth2Login(@RegisteredOAuth2AuthorizedClient("sso") authorizedClient: OAuth2AuthorizedClient, response: HttpServletResponse): String {
        val accessToken = authorizedClient.accessToken
        val refreshToken = authorizedClient.refreshToken
        accessToken?.run {
            CookieUtils.addCookie(response, Oauth2Constant.ACCESS_TOKEN, this.tokenValue, this.maxAge())
        }
        refreshToken?.run {
            CookieUtils.addCookie(response, Oauth2Constant.REFRESH_TOKEN, this.tokenValue, this.maxAge())
        }
        return "redirect:${Oauth2Constant.SUCCESS_URL}"
    }

    @PostMapping("/oauth2/logout", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun oauth2Logout(@RegisteredOAuth2AuthorizedClient("sso") authorizedClient: OAuth2AuthorizedClient, request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication): String {
        oauth2Service.logout(authorizedClient)
        SecurityContextLogoutHandler().logout(request, response, authentication)
        return "redirect:/"
    }
}