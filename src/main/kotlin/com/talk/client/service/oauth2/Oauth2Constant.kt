package com.talk.client.service.oauth2

object Oauth2Constant {
    const val SUCCESS_URL = "/friends"
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
    const val REDIRECT_URI_REQUEST_NAME = "redirect_uri"
    val AUTHORIZATION_REQUEST_NAME = HttpCookieOauth2AuthorizationRequestRepository::class.java
            .name + ".AUTHORIZATION_REQUEST"
}