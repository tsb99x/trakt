package io.github.tsb99x.trakt.exception

import java.util.*

data class ApiErrorResponse(
    val requestId: UUID,
    val errorDescription: String
)
