package io.github.tsb99x.trakt.web.controller

import io.github.tsb99x.trakt.config.ConfigProperties
import io.github.tsb99x.trakt.service.MessageService
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MessageController(
    private val configProperties: ConfigProperties,
    private val service: MessageService
) {

    @GetMapping("/")
    fun root(
        model: Model
    ): String {

        model["version"] = configProperties.version
        model["messages"] = service.getAll()

        return "index"

    }

    @PostMapping("/")
    @Secured("ROLE_ADMIN")
    fun addMessage(
        msg: String
    ): String {

        service.add(msg)
        return "redirect:/"

    }

}
