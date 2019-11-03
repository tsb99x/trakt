package io.github.tsb99x.trakt.exception

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.github.tsb99x.trakt.REQUEST_ID
import io.github.tsb99x.trakt.classLogger
import io.github.tsb99x.trakt.toUUID
import org.slf4j.MDC
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlers {

    private val logger = classLogger()

    @ExceptionHandler(GenericException::class)
    fun responseOnGenericException(
        ex: GenericException
    ): ResponseEntity<ApiErrorResponse> {

        val desc = "Generic exception occurred"
        val body = buildResponse(ex.localizedMessage)

        logger.info(desc, ex)

        return ResponseEntity(body, HttpStatus.UNPROCESSABLE_ENTITY)

    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun respondOnMalformedMessage(
        ex: HttpMessageNotReadableException
    ): ResponseEntity<ApiErrorResponse> {

        val cause = ex.rootCause
        val (status, desc) = when (cause) {
            is MissingKotlinParameterException ->
                HttpStatus.UNPROCESSABLE_ENTITY to
                    "Expected '${cause.path[0].fieldName}' to be present"
            is MismatchedInputException ->
                HttpStatus.UNPROCESSABLE_ENTITY to
                    "Expected '${cause.path[0].fieldName}' to be ${cause.targetType.simpleName}"
            else ->
                HttpStatus.BAD_REQUEST to
                    "Invalid JSON request body"
        }

        val body = buildResponse(desc)

        logger.warn(desc, ex)

        return ResponseEntity(body, status)

    }

    @ExceptionHandler(Throwable::class)
    fun responseOnThrowable(
        t: Throwable
    ): ResponseEntity<ApiErrorResponse> {

        val desc = "Internal server error"
        val body = buildResponse(desc)

        logger.error(desc, t)

        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)

    }

    private fun buildResponse(
        desc: String
    ): ApiErrorResponse {

        val requestId = MDC.get(REQUEST_ID).toUUID()
        return ApiErrorResponse(requestId, desc)

    }

}
