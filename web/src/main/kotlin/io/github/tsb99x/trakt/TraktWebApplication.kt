package io.github.tsb99x.trakt

import io.github.tsb99x.trakt.config.WebConfig
import io.github.tsb99x.trakt.config.initCoreConfig
import io.github.tsb99x.trakt.config.initDataConfig
import io.github.tsb99x.trakt.config.initWebConfig
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder

fun initServer(
    webConfig: WebConfig
): Server {

    return Server(webConfig.serverPort).apply {

        handler = ServletContextHandler(ServletContextHandler.NO_SESSIONS or ServletContextHandler.NO_SECURITY).apply {
            contextPath = "/"

            addServlet(ServletHolder(webConfig.messageServlet), "/")

        }

    }

}

fun main() {

    val dataConfig = initDataConfig()
    val coreConfig = initCoreConfig(dataConfig)
    val webConfig = initWebConfig(coreConfig)

    initServer(webConfig).apply {

        start()
        join()

    }

}
