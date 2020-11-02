package io.github.tsb99x.trakt.api.model

import java.util.*

data class LoginResponse(
    val requestId: UUID,
    val apiToken: UUID
)
