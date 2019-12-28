package io.github.tsb99x.trakt.web.servlet

import io.github.tsb99x.trakt.core.service.MessageService
import io.github.tsb99x.trakt.web.sendHtml
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.WebContext
import java.util.*
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_OK

class MessageServlet(
    private val templateEngine: TemplateEngine,
    private val service: MessageService
) : HttpServlet() {

    override fun doGet(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {

        val context = WebContext(request, response, servletContext, Locale.getDefault(), mapOf(
            "version" to "development",
            "messages" to service.getAll()
        ))

        response.sendHtml(SC_OK, templateEngine, "templates/index.html", context)

    }

    override fun doPost(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {

        val msg = request.getParameter("msg")

        service.add(msg)

        response.sendRedirect("/")

    }

}
