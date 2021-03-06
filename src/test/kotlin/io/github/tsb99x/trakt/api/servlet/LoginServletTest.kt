package io.github.tsb99x.trakt.api.servlet

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.tsb99x.trakt.StartServerExtension
import io.github.tsb99x.trakt.api.model.LoginRequest
import io.github.tsb99x.trakt.api.model.LoginResponse
import io.github.tsb99x.trakt.postJson
import io.github.tsb99x.trakt.ADMIN_USER
import io.github.tsb99x.trakt.core.API_V1_PATH
import io.github.tsb99x.trakt.INTEGRATION
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.net.http.HttpClient
import javax.servlet.http.HttpServletResponse.SC_OK

@Tag(INTEGRATION)
class LoginServletTest {

    @JvmField
    @RegisterExtension
    val serverExtension = StartServerExtension()
    private val serverPort = serverExtension.serverPort

    private val objectMapper = jacksonObjectMapper()
    private val httpClient = HttpClient.newHttpClient()

    @Test
    fun `expect login to work`() {

        val request = LoginRequest(ADMIN_USER.name, "admin")

        val httpResponse = httpClient.postJson(serverPort, "$API_V1_PATH/auth/login", objectMapper, request)
        assertEquals(SC_OK, httpResponse.statusCode())

        val response: LoginResponse = objectMapper.readValue(httpResponse.body())
        assertNotNull(response)

    }

}
