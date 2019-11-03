package io.github.tsb99x.trakt.authentication

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Tag("integration")
class AuthenticationControllerTest @Autowired constructor(
    private val restTemplate: TestRestTemplate
) {

    @Test
    fun `expect authentication to work`() {

        val request = AuthenticationRequest("admin", "admin")
        val responseEntity = restTemplate.postForEntity<AuthenticationResponse>("/api/v1/authenticate", request)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)

        val response = responseEntity.body
        assertNotNull(response)

    }

}
