package io.github.tsb99x.trakt.data

import java.time.LocalDateTime
import java.util.*

data class MessageEntity(
    val id: UUID,
    val text: String,
    val createdAt: LocalDateTime
)
