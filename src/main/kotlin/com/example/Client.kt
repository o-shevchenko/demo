package com.example

import io.micronaut.http.HttpHeaders.ACCEPT
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Headers
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.CircuitBreaker

@Client(
    value = "\${controller.url}",
    path = "\${controller.path}",
)
@CircuitBreaker(
    attempts = "\${controller.retry.attempts}",
    delay = "\${controller.retry.delay}",
    multiplier = "\${controller.retry.multiplier}",
    reset = "\${controller.retry.reset}",
)
@Headers(Header(name = ACCEPT, value = "application/json"))
interface Client {

    @Post("/{id}/test")
    fun test(@PathVariable id: String): HttpResponse<String>

}

