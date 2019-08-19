package io.github.tsb99x.trakt.exception

import io.github.tsb99x.trakt.config.ConfigProperties
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.WebRequest

@Controller
class CustomErrorController(

    val configProperties: ConfigProperties,
    val errorAttributes: ErrorAttributes

) : ErrorController {

    @RequestMapping
    fun error(model: Model, request: WebRequest): String {

        model["version"] = configProperties.version

        val attrs = errorAttributes.getErrorAttributes(request, false)
        model.addAllAttributes(attrs)

        return "error"

    }

    override fun getErrorPath(): String {

        return "/error"

    }


}
