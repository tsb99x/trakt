package io.github.tsb99x.trakt.data

import java.time.LocalDateTime
import java.util.*

data class ApiTokenEntity(
    val id: UUID,
    val userId: UUID,
    val createdAt: LocalDateTime
)
