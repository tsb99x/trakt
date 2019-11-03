package io.github.tsb99x.trakt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.ResultSet
import java.time.LocalDateTime
import java.util.*

const val REQUEST_ID = "requestId"

fun String.toUUID(): UUID =
    UUID.fromString(this)

fun ResultSet.getUUID(columnLabel: String): UUID =
    this.getObject(columnLabel, UUID::class.java)

fun ResultSet.getLocalDateTime(columnLabel: String): LocalDateTime =
    this.getObject(columnLabel, LocalDateTime::class.java)

fun Any.classLogger(): Logger =
    LoggerFactory.getLogger(this::class.java)
