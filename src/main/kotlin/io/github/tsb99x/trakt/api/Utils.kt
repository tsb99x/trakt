package io.github.tsb99x.trakt.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val AUTHORIZATION = "Authorization"
const val APPLICATION_JSON = "application/json"
const val CONTENT_TYPE = "Content-Type"

inline fun <reified T> HttpServletRequest.reqAttr(name: String): T =
    getAttribute(name) as T

inline fun <reified T> HttpServletRequest.reqBody(objectMapper: ObjectMapper): T =
    objectMapper.readValue(inputStream)

fun HttpServletResponse.sendJson(statusCode: Int, objectMapper: ObjectMapper, value: Any) {
    status = statusCode
    contentType = APPLICATION_JSON
    objectMapper.writeValue(outputStream, value)
}
