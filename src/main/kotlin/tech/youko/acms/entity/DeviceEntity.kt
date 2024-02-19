package tech.youko.acms.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "device")
data class DeviceEntity(
    @Id
    @Column(nullable = false, length = 40)
    var id: String?,

    @Column(nullable = false, length = 100)
    var name: String?,

    @Column(length = 500)
    var description: String? = null
)
