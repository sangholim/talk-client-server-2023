package com.talk.client.service.profile

import com.talk.client.service.friend.FriendView

data class ProfileView(
        val id: String,
        val email: String,
        val name: String,
        val friends: List<FriendView>? = emptyList()
)