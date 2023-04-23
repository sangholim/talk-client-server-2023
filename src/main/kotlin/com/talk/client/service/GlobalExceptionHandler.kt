package com.talk.client.service

import com.talk.client.service.error.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [WebClientResponseException::class])
    fun handlerWebClientResponseException(e: WebClientResponseException): ResponseEntity<ErrorResponse> =
            ResponseEntity(ErrorResponse.from(e), e.statusCode)
}