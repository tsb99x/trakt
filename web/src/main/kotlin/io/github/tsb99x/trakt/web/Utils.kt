package io.github.tsb99x.trakt.web

import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.WebContext
import javax.servlet.http.HttpServletResponse

const val TEXT_HTML = "text/html"

fun HttpServletResponse.sendHtml(
    statusCode: Int,
    templateEngine: TemplateEngine,
    templateName: String,
    webContext: WebContext
) {

    status = statusCode
    contentType = TEXT_HTML
    templateEngine.process(templateName, webContext, writer)

}
