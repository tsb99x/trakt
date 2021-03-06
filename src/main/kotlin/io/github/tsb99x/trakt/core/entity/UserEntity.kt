package io.github.tsb99x.trakt.core.entity

import java.util.*

data class UserEntity(
    val id: UUID,
    val name: String,
    val hash: String,
    val enabled: Boolean
)
