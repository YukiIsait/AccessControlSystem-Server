package tech.youko.acms.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Table
import tech.youko.acms.entity.id.AccessInfoId

@Entity
@Table(name = "access_info")
data class AccessInfoEntity(
    @EmbeddedId
    var id: AccessInfoId?,

    @Column(length = 500)
    var description: String? = null
)
