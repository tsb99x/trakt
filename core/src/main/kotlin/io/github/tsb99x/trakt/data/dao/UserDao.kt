package io.github.tsb99x.trakt.data.dao

import io.github.tsb99x.trakt.core.entity.UserEntity
import java.util.*

interface UserDao {

    fun selectOneByUsername(
        username: String
    ): UserEntity?

    fun selectOneById(
        id: UUID
    ): UserEntity?

}
