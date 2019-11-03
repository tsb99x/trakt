package io.github.tsb99x.trakt.authentication

import java.util.*

data class AuthenticationResponse(
    val requestId: UUID,
    val apiToken: UUID
)
