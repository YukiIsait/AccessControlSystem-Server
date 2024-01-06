package tech.youko.acms.entity

import jakarta.persistence.*

@Entity
@Table(name = "access_info")
data class AccessInfoEntity(
    @EmbeddedId
    var id: AccessInfoId,

    @Column(length = 500)
    var description: String? = null
)
