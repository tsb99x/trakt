package io.github.tsb99x.trakt.data.dao

import io.github.tsb99x.trakt.core.entity.ApiTokenEntity
import java.util.*

interface ApiTokenDao {

    fun insert(
        apiToken: ApiTokenEntity
    )

    fun selectOneById(
        id: UUID
    ): ApiTokenEntity?

    fun deleteById(
        id: UUID
    )

    fun updateLastUsedAtById(
        id: UUID
    )

}
