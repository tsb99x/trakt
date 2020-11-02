package io.github.tsb99x.trakt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.tsb99x.trakt.api.filter.AuthorizationFilter
import io.github.tsb99x.trakt.api.filter.ExceptionFilter
import io.github.tsb99x.trakt.api.filter.RequestFilter
import io.github.tsb99x.trakt.api.servlet.DummyServlet
import io.github.tsb99x.trakt.api.servlet.LoginServlet
import io.github.tsb99x.trakt.api.servlet.LogoutServlet
import io.github.tsb99x.trakt.core.API_V1_PATH
import io.github.tsb99x.trakt.core.service.AuthorizationService
import io.github.tsb99x.trakt.data.config.DataConfig
import io.github.tsb99x.trakt.data.config.initDataConfig
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.FilterHolder
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletContextHandler.NO_SECURITY
import org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS
import org.eclipse.jetty.servlet.ServletHolder
import java.util.*
import javax.servlet.DispatcherType

fun initServer(
    port: Int,
    dataConfig: DataConfig
): Server {

    val objectMapper = jacksonObjectMapper()

    val authorizationService = AuthorizationService(dataConfig.apiTokenDao, dataConfig.userDao)

    val exceptionFilter = ExceptionFilter(objectMapper)
    val requestFilter = RequestFilter()
    val authorizationFilter = AuthorizationFilter(authorizationService, "/auth/login")

    return Server(port).apply {

        handler = ServletContextHandler(NO_SESSIONS or NO_SECURITY).apply {
            contextPath = API_V1_PATH

            addServlet(ServletHolder(LoginServlet(authorizationService, objectMapper)), "/auth/login")
            addServlet(ServletHolder(LogoutServlet(authorizationService)), "/auth/logout")
            addServlet(ServletHolder(DummyServlet(objectMapper)), "/dummy")

            addFilter(FilterHolder(requestFilter), "/*", EnumSet.of(DispatcherType.REQUEST))
            addFilter(FilterHolder(exceptionFilter), "/*", EnumSet.of(DispatcherType.REQUEST))
            addFilter(FilterHolder(authorizationFilter), "/*", EnumSet.of(DispatcherType.REQUEST))
        }

    }

}

fun main() {

    initServer(8080, initDataConfig()).apply {

        start()
        join()

    }

}
