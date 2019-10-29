package io.github.tsb99x.trakt

import io.github.tsb99x.trakt.message.MessageEntity
import org.mockito.Mockito
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDateTime
import java.util.*

inline fun <reified T> mock(): T =
    Mockito.mock(T::class.java)

fun dumbMessageEntity(msg: String) =
    MessageEntity(UUID.randomUUID(), msg, LocalDateTime.now())

fun JdbcTemplate.truncate(tableName: String) =
    this.execute("TRUNCATE TABLE $tableName")
