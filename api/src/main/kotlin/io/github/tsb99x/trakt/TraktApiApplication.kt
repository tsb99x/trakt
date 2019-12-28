package io.github.tsb99x.trakt

import io.github.tsb99x.trakt.config.ApiConfig
import io.github.tsb99x.trakt.config.initApiConfig
import io.github.tsb99x.trakt.config.initCoreConfig
import io.github.tsb99x.trakt.config.initDataConfig
import io.github.tsb99x.trakt.core.API_V1_PATH
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.FilterHolder
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletContextHandler.NO_SECURITY
import org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS
import org.eclipse.jetty.servlet.ServletHolder
import java.util.*
import javax.servlet.DispatcherType

fun initServer(
    apiConfig: ApiConfig
): Server {

    return Server(apiConfig.serverPort).apply {

        handler = ServletContextHandler(NO_SESSIONS or NO_SECURITY).apply {
            contextPath = API_V1_PATH

            addServlet(ServletHolder(apiConfig.loginServlet), "/auth/login")
            addServlet(ServletHolder(apiConfig.logoutServlet), "/auth/logout")
            addServlet(ServletHolder(apiConfig.dummyServlet), "/dummy")

            addFilter(FilterHolder(apiConfig.requestFilter), "/*", EnumSet.of(DispatcherType.REQUEST))
            addFilter(FilterHolder(apiConfig.exceptionFilter), "/*", EnumSet.of(DispatcherType.REQUEST))
            addFilter(FilterHolder(apiConfig.authorizationFilter), "/*", EnumSet.of(DispatcherType.REQUEST))
        }

    }

}

fun main() {

    val dataConfig = initDataConfig()
    val coreConfig = initCoreConfig(dataConfig)
    val apiConfig = initApiConfig(coreConfig)

    initServer(apiConfig).apply {

        start()
        join()

    }

}
