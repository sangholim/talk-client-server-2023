package com.talk.client.service.client

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "resource-client")
data class ResourceClientProperty (
        val baseUrl: String
)