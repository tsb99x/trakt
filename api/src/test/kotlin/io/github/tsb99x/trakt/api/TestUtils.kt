package io.github.tsb99x.trakt.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.tsb99x.trakt.config.ApiConfig
import io.github.tsb99x.trakt.config.CoreConfig
import io.github.tsb99x.trakt.config.initCoreConfig
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

const val LOWEST_ALLOWED_DYN_IANA_PORT = 49152
const val HIGHEST_ALLOWED_DYN_IANA_PORT = 65535

fun initTestApiConfig(
    coreConfig: CoreConfig
) =
    ApiConfig(
        coreConfig,
        Random.nextInt(LOWEST_ALLOWED_DYN_IANA_PORT, HIGHEST_ALLOWED_DYN_IANA_PORT)
    )

class StartServerExtension : BeforeEachCallback, AfterEachCallback {

    private lateinit var server: Server

    private val dataConfig = initTestDataConfig()
    private val coreConfig = initCoreConfig(dataConfig)
    private val apiConfig = initTestApiConfig(coreConfig)

    val serverPort = apiConfig.serverPort

    override fun beforeEach(
        context: ExtensionContext
    ) {

        server = initServer(apiConfig).apply {
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
