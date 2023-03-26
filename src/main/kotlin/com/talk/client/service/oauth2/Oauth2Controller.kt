package com.talk.client.service.oauth2

import com.talk.client.service.util.CookieUtils
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Oauth2Controller {

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

        return "index"
    }
}