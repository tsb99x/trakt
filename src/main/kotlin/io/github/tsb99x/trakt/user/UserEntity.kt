package io.github.tsb99x.trakt.user

data class UserEntity(

    val id: Long? = null,
    val name: String,
    val hash: String,
    val enabled: Boolean = true,
    val roles: MutableSet<RoleEntity> = mutableSetOf()

)
