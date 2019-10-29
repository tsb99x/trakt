package io.github.tsb99x.trakt.exception

import io.github.tsb99x.trakt.config.ConfigProperties
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.logging.Level
import java.util.logging.Logger

@ControllerAdvice
class GeneralExceptionHandlers(

    private val configProperties: ConfigProperties

) {

    val logger: Logger = Logger.getLogger(this::class.simpleName)

    @ExceptionHandler(GenericException::class)
    fun customOne(ex: GenericException, model: Model): String {

        logger.log(Level.SEVERE, "UID: ${ex.uid}", ex)

        model["version"] = configProperties.version
        model["message"] = "Something went wrong! Support UID: ${ex.uid}"

        return "error"

    }

}
