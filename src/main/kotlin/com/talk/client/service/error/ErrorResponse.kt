package com.talk.client.service.error

import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClientResponseException

class ErrorResponse private constructor(
        val status: HttpStatus,
        val message: String?
) {
    companion object {
        fun from(e: WebClientResponseException): ErrorResponse? = e.getResponseBodyAs(ErrorResponse::class.java)
    }
}