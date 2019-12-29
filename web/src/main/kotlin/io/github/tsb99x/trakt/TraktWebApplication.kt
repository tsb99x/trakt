package io.github.tsb99x.trakt

import io.github.tsb99x.trakt.core.service.MessageService
import io.github.tsb99x.trakt.data.config.DataConfig
import io.github.tsb99x.trakt.data.config.initDataConfig
import io.github.tsb99x.trakt.web.servlet.MessageServlet
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

fun initServer(
    port: Int,
    dataConfig: DataConfig
): Server {

    val templateEngine = TemplateEngine().apply {
        setTemplateResolver(
            ClassLoaderTemplateResolver().apply {
                templateMode = TemplateMode.HTML
            }
        )
    }

    val messageService = MessageService(dataConfig.messageDao)

    return Server(port).apply {

        handler = ServletContextHandler(ServletContextHandler.NO_SESSIONS or ServletContextHandler.NO_SECURITY).apply {
            contextPath = "/"

            addServlet(ServletHolder(MessageServlet(templateEngine, messageService)), "/")

        }

    }

}

fun main() {

    initServer(8080, initDataConfig()).apply {

        start()
        join()

    }

}
