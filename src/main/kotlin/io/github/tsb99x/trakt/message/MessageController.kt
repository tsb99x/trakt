package io.github.tsb99x.trakt.message

import io.github.tsb99x.trakt.config.ConfigProperties
import io.github.tsb99x.trakt.exception.GenericException
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime
import java.util.*

@Controller
class MessageController(

    private val configProperties: ConfigProperties,
    private val service: MessageService

) {

    @GetMapping("/")
    fun root(model: Model): String {

        model["version"] = configProperties.version
        model["messages"] = service.getAll()

        return "index"

    }

    @PostMapping("/")
    @Secured("ROLE_ADMIN")
    fun addMessage(msg: String): String {

        if (msg.isEmpty()) {
            throw RuntimeException("msg must not be empty")
        }

        if (msg == "123") {
            throw GenericException("msg must not be 123")
        }

        val entity = MessageEntity(UUID.randomUUID(), msg, LocalDateTime.now())
        service.add(entity)
        return "redirect:/"

    }

}
