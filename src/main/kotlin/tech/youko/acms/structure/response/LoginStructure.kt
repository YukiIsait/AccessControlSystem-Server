package tech.youko.acms.structure.response

import tech.youko.acms.entity.UserEntity

data class LoginStructure(
    val token: String,
    val information: UserEntity
)
