package io.github.tsb99x.trakt.rest.model

import java.util.*

data class ApiErrorResponse(
    val requestId: UUID,
    val errorDescription: String
)
