package io.github.tsb99x.trakt.api.model

import java.util.*

data class ApiErrorResponse(
    val requestId: UUID,
    val errorDescription: String
)
