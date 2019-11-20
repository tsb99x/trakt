package io.github.tsb99x.trakt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

const val API_TOKEN_ID_ATTRIBUTE = "api-token-id"
const val API_TOKEN_MUST_BE_UUID = "API token must be UUID"
const val API_V1_PATH = "/api/v1"
const val AUTHORIZATION_HEADER_MUST_BE_PRESENT = "Authorization header must be present"
const val BEARER = "Bearer"
const val INCORRECT_API_TOKEN = "Incorrect API token"
const val ONLY_AUTHORIZATION_BEARER_IS_SUPPORTED = "Only Authorization: Bearer is supported"
const val REQUEST_ID = "requestId"
const val USER_ATTRIBUTE = "user"
const val USER_IS_NOT_ENABLED = "User is not enabled"
const val USER_NOT_FOUND = "User not found"
const val USERNAME_AND_PASSWORD_COMBO_NOT_FOUND = "Such combination of username and password was not found"

fun String.toUUID(): UUID =
    UUID.fromString(this)

fun Instant.toUTC(): OffsetDateTime =
    this.atOffset(ZoneOffset.UTC)

fun Instant.minusMinutes(minutes: Long): Instant =
    this.minus(minutes, ChronoUnit.MINUTES)

fun ResultSet.getUUID(columnLabel: String): UUID =
    this.getObject(columnLabel, UUID::class.java)

fun ResultSet.getInstant(columnLabel: String): Instant =
    this.getObject(columnLabel, OffsetDateTime::class.java).toInstant()

fun Any.classLogger(): Logger =
    LoggerFactory.getLogger(this::class.java)

fun <T> tryOrNull(f: () -> T): T? =
    try { f() } catch (_: Exception) { null }

fun <T> JdbcTemplate.queryForObjectOrNull(sql: String, args: Array<out Any>, rowMapper: RowMapper<T>): T? =
    try { this.queryForObject(sql, args, rowMapper) } catch (_: EmptyResultDataAccessException) { null }
