package io.github.tsb99x.trakt.config

import io.github.tsb99x.trakt.core.getConfigVariable
import io.github.tsb99x.trakt.web.servlet.MessageServlet
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

const val DEFAULT_SERVER_PORT = "8080"

fun initWebConfig(
    coreConfig: CoreConfig
) =
    WebConfig(
        coreConfig,
        getConfigVariable("server.port", DEFAULT_SERVER_PORT).toInt()
    )

class WebConfig(
    coreConfig: CoreConfig,
    val serverPort: Int
) {

    private val templateEngine =
        TemplateEngine().apply {
            setTemplateResolver(
                ClassLoaderTemplateResolver().apply {
                    templateMode = TemplateMode.HTML
                }
            )
        }

    val messageServlet =
        MessageServlet(templateEngine, coreConfig.messageService)

}
