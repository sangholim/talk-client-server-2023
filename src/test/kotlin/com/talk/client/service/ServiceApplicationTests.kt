package com.talk.client.service

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.MockkBeans
import com.talk.client.service.config.Oauth2Config
import com.talk.client.service.config.SecurityConfig
import com.talk.client.service.oauth2.Oauth2Service
import com.talk.client.service.oauth2.Oauth2SsoLogoutHandler
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.web.reactive.function.client.WebClient

@MockkBeans(
        MockkBean(SecurityConfig::class, relaxed = true),
        MockkBean(Oauth2Config::class, relaxed = true),
        MockkBean(Oauth2Service::class, relaxed = true),
        MockkBean(OAuth2AuthorizedClientManager::class, relaxUnitFun = true),
        MockkBean(Oauth2SsoLogoutHandler::class, relaxUnitFun = true),
        MockkBean(name = "oauth2Client", classes = [WebClient::class], relaxUnitFun = true),
        MockkBean(name = "resourceClient", classes = [WebClient::class], relaxUnitFun = true)
)
@SpringBootTest
class ServiceApplicationTests {

    @Test
    fun contextLoads() {
    }

}
