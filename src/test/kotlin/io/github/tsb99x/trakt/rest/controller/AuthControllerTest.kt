package io.github.tsb99x.trakt.rest.controller

import io.github.tsb99x.trakt.ADMIN_USER
import io.github.tsb99x.trakt.API_V1_PATH
import io.github.tsb99x.trakt.INTEGRATION
import io.github.tsb99x.trakt.rest.model.LoginRequest
import io.github.tsb99x.trakt.rest.model.LoginResponse
import io.github.tsb99x.trakt.toUri
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@Tag(INTEGRATION)
class AuthControllerTest @Autowired constructor(
    private val restTemplate: TestRestTemplate
) {

    @Test
    fun `expect login to work`() {

        val responseEntity = login()

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)

    }

    @Test
    fun `expect logout to work`() {

        val token = login().body!!.apiToken

        val headers = HttpHeaders().apply { setBearerAuth(token.toString()) }
        val request = RequestEntity<Any>(headers, HttpMethod.POST, "$API_V1_PATH/auth/logout".toUri())
        val responseEntity = restTemplate.exchange<Any>(request)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNull(responseEntity.body)

    }

    private fun login(): ResponseEntity<LoginResponse> {

        val request = LoginRequest(ADMIN_USER.name, "admin")
        return restTemplate.postForEntity("$API_V1_PATH/auth/login", request)

    }

}
