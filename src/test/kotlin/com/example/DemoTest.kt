package com.example

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.hateoas.JsonError
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest
class DemoTest {

    @Inject
    private lateinit var client: Client

    @Test
    fun testPass() {
        val result = client.test("SUCCESS")
        Assertions.assertEquals(HttpStatus.OK, result.status)
    }

    @Test
    fun testFail() {
        val exception = Assertions.assertThrows(HttpClientResponseException::class.java) {
            client.test("FAIL")
        }
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.status)
    }

}
