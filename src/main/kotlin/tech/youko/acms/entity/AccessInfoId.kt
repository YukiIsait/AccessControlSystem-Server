package tech.youko.acms.entity

import jakarta.persistence.*
import java.io.Serializable

@Embeddable
data class AccessInfoId(
    @Column(nullable = false, length = 40)
    var deviceId: String,

    @Column(nullable = false, length = 40)
    var userId: String
) : Serializable
