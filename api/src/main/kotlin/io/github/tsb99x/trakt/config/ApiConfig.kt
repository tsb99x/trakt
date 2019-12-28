package io.github.tsb99x.trakt.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.tsb99x.trakt.api.filter.AuthorizationFilter
import io.github.tsb99x.trakt.api.filter.ExceptionFilter
import io.github.tsb99x.trakt.api.filter.RequestFilter
import io.github.tsb99x.trakt.api.servlet.DummyServlet
import io.github.tsb99x.trakt.api.servlet.LoginServlet
import io.github.tsb99x.trakt.api.servlet.LogoutServlet
import io.github.tsb99x.trakt.core.getConfigVariable

const val DEFAULT_SERVER_PORT = "8080"

fun initApiConfig(
    coreConfig: CoreConfig
) =
    ApiConfig(
        coreConfig,
        getConfigVariable("server.port", DEFAULT_SERVER_PORT).toInt()
    )

class ApiConfig(
    coreConfig: CoreConfig,
    val serverPort: Int
) {

    private val objectMapper =
        jacksonObjectMapper()

    val exceptionFilter =
        ExceptionFilter(objectMapper)

    val requestFilter =
        RequestFilter()

    val authorizationFilter =
        AuthorizationFilter(coreConfig.authorizationService, "/auth/login")

    val loginServlet =
        LoginServlet(coreConfig.authorizationService, objectMapper)

    val logoutServlet =
        LogoutServlet(coreConfig.authorizationService)

    val dummyServlet =
        DummyServlet(objectMapper)

}
