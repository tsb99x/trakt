package io.github.tsb99x.trakt.user

import java.util.*

data class UserEntity(

    val id: UUID,
    val name: String,
    val hash: String,
    val enabled: Boolean = true,
    val roles: MutableSet<RoleEntity> = mutableSetOf()

)
