package com.talk.client.service.oauth2

import org.springframework.security.oauth2.core.AbstractOAuth2Token
import java.time.Duration


fun AbstractOAuth2Token.maxAge(): Int {
    val expired = expiresAt ?: return 300
    val issued = issuedAt ?: return 300
    val age = Duration.between(issued, expired)
    return age.toSeconds().toInt()
}