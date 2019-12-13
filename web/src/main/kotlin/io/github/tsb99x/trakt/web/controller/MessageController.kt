package io.github.tsb99x.trakt.web.controller

import io.github.tsb99x.trakt.core.service.MessageService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MessageController(
    private val service: MessageService
) {

    @GetMapping("/")
    fun root(
        model: Model
    ): String {

        model["version"] = "development" // FIXME : SHOULD BE A CONFIG VAR!
        model["messages"] = service.getAll()

        return "index"

    }

    @PostMapping("/")
    // FIXME : ONLY FOR ADMIN!
    fun addMessage(
        msg: String
    ): String {

        service.add(msg)
        return "redirect:/"

    }

}
