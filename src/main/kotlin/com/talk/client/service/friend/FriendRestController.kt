package com.talk.client.service.friend

import com.talk.client.service.profile.ProfileClient
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
class FriendRestController(
        private val client: FriendClient,
        private val profileClient: ProfileClient
) {
    /**
     * resource 서버로 친구 생성 요청
     */
    @PostMapping(value = ["/api/friends"])
    @ResponseStatus(HttpStatus.CREATED)
    fun insert(authentication: JwtAuthenticationToken, @RequestBody payload: FriendCreationPayload): FriendView? {
        return client.create(authentication.token.tokenValue, payload)
    }

    @GetMapping("/api/friends")
    fun getProfileFriends(authentication: JwtAuthenticationToken): List<FriendView> {
        val profile = profileClient.get(authentication.token.tokenValue)
        return profile?.friends ?: emptyList()
    }
}

