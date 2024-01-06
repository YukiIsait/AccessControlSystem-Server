package tech.youko.acms.entity

import jakarta.persistence.*

@Entity
@Table(name = "device_info")
data class DeviceInfoEntity(
    @Id
    @Column(nullable = false, length = 40)
    var id: String,

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(length = 500)
    var description: String? = null
)
