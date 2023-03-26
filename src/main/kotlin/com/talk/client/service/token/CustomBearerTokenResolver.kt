package com.talk.client.service.token

import com.talk.client.service.oauth2.Oauth2Constant
import com.talk.client.service.util.CookieUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver

/**
 * 추가 토큰 처리를 먼저 진행후, 기존 로직을 수행한다
 */
class CustomBearerTokenResolver : BearerTokenResolver {
    /**
     * spring security oauth2ResourceServer token-resolver 설정 안하는경우, 지원 하는 기본 클래스
     * spring 에서 bean 으로 지원 하지 않기 때문에 직접 주입
     */
    private val defaultBearerTokenResolver: DefaultBearerTokenResolver = DefaultBearerTokenResolver()

    /**
     * 쿠키에 저장된 액세스 토큰이 있으면 가져 오도록 처리
     */
    override fun resolve(request: HttpServletRequest): String? {
        // 쿠키에 액세스 토큰이 있는 경우 헤더에 추가한다
        val tokenCookie = CookieUtils.getCookie(request, Oauth2Constant.ACCESS_TOKEN)
        if(tokenCookie != null) {
            return tokenCookie.value
        }
        return defaultBearerTokenResolver.resolve(request)
    }
}