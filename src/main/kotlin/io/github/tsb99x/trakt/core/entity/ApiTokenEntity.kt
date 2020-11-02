package io.github.tsb99x.trakt.core.entity

import java.time.Instant
import java.util.*

data class ApiTokenEntity(
    val id: UUID,
    val userId: UUID,
    val createdAt: Instant,
    val lastUsedAt: Instant
)
