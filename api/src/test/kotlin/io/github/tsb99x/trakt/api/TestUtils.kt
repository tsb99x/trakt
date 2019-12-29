package io.github.tsb99x.trakt.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.tsb99x.trakt.core.BEARER
import io.github.tsb99x.trakt.core.toUri
import io.github.tsb99x.trakt.data.initTestDataConfig
import io.github.tsb99x.trakt.initServer
import org.eclipse.jetty.server.Server
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*
import kotlin.random.Random

class StartServerExtension : BeforeEachCallback, AfterEachCallback {

    private lateinit var server: Server

    val serverPort = Random.nextInt(49152, 65535)

    override fun beforeEach(
        context: ExtensionContext
    ) {

        server = initServer(serverPort, initTestDataConfig()).apply {
            start()
        }

    }

    override fun afterEach(
        context: ExtensionContext
    ) {

        server.stop()

    }

}

fun HttpClient.post(
    serverPort: Int,
    relUrl: String,
    token: UUID? = null
): HttpResponse<String> =
    send(
        HttpRequest.newBuilder("http://localhost:$serverPort$relUrl".toUri())
            .header(AUTHORIZATION, "$BEARER $token")
            .POST(HttpRequest.BodyPublishers.noBody())
            .build(),
        HttpResponse.BodyHandlers.ofString()
    )

fun <T> HttpClient.postJson(
    serverPort: Int,
    relUrl: String,
    objectMapper: ObjectMapper,
    request: T,
    token: UUID? = null
): HttpResponse<String> =
    send(
        HttpRequest.newBuilder("http://localhost:$serverPort$relUrl".toUri())
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .header(AUTHORIZATION, "$BEARER $token")
            .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(request)))
            .build(),
        HttpResponse.BodyHandlers.ofString()
    )
