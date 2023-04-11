package com.talk.client.service.config

import com.talk.client.service.client.ResourceClientProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(ResourceClientProperty::class)
class ResourceClientConfig {

    @Bean
    fun resourceClient(resourceClientProperty: ResourceClientProperty): WebClient =
            WebClient.builder().baseUrl(resourceClientProperty.baseUrl).build()
}