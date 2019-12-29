package io.github.tsb99x.trakt.api.servlet

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.tsb99x.trakt.api.StartServerExtension
import io.github.tsb99x.trakt.api.model.LoginRequest
import io.github.tsb99x.trakt.api.model.LoginResponse
import io.github.tsb99x.trakt.api.post
import io.github.tsb99x.trakt.api.postJson
import io.github.tsb99x.trakt.core.ADMIN_USER
import io.github.tsb99x.trakt.core.INTEGRATION
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.net.http.HttpClient
import javax.servlet.http.HttpServletResponse.SC_OK

@Tag(INTEGRATION)
class LogoutServletTest {

    @JvmField
    @RegisterExtension
    val serverExtension = StartServerExtension()
    private val serverPort = serverExtension.serverPort

    private val objectMapper = jacksonObjectMapper()
    private val httpClient = HttpClient.newHttpClient()

    @Test
    fun `expect logout to work`() {

        val loginRequest = LoginRequest(ADMIN_USER.name, "admin")
        val httpLoginResponse = httpClient.postJson(serverPort, "/api/v1/auth/login", objectMapper, loginRequest)
        val loginResponse: LoginResponse = objectMapper.readValue(httpLoginResponse.body())

        val token = loginResponse.apiToken

        val httpLogoutResponse = httpClient.post(serverPort, "/api/v1/auth/logout", token)
        assertEquals(SC_OK, httpLogoutResponse.statusCode())
        assertTrue(httpLogoutResponse.body().isEmpty())

    }

}
