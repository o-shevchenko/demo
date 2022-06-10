package com.example

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.hateoas.JsonError
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@ExecuteOn(TaskExecutors.IO)
@Controller(
    value = "/api",
    consumes = [MediaType.APPLICATION_JSON],
    produces = [MediaType.APPLICATION_JSON]
)
class Controller() {


    @Post(uri = "/{id}/test")
    fun test(
        @PathVariable id: String
    ): Publisher<MutableHttpResponse<String>> {
        return Mono.just(id)
            .map {
                when (it) {
                    "1" -> HttpResponse.ok(it)
                    else -> throw java.lang.IllegalArgumentException()
                }
            }
            .defaultIfEmpty(HttpResponse.ok())
            .onErrorResume { exception ->
                Mono.just(HttpResponse.badRequest())
            }
    }


    @Error
    fun handleException(exception: Throwable): HttpResponse<JsonError> {
        return HttpResponse.badRequest(JsonError(exception.message))
    }

}
