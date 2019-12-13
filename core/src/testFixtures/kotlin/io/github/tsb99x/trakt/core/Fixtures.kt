package io.github.tsb99x.trakt.core

import io.github.tsb99x.trakt.core.entity.MessageEntity
import io.github.tsb99x.trakt.core.entity.UserEntity
import java.net.URI
import java.time.Instant
import java.util.*

const val INTEGRATION = "integration"

val ADMIN_USER = UserEntity(
    "5007f5be-bef7-428e-b40d-534d27c92f22".toUUID(),
    "admin",
    "\$2a\$11\$HXUtm.6N8OmQ9biKeW.vpOwmW5uoEG5GKa4J49e1RCM0RKHvnb23i",
    true
)

fun dumbMessageEntity(msg: String) =
    MessageEntity(UUID.randomUUID(), msg, Instant.now())

fun String.toUri() =
    URI(this)
