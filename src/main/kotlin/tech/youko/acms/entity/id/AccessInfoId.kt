package tech.youko.acms.entity.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AccessInfoId(
    @Column(nullable = false, length = 40)
    var deviceId: String,

    @Column(nullable = false, length = 40)
    var userId: String
) : Serializable
