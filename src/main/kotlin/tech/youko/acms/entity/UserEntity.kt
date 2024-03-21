package tech.youko.acms.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
data class UserEntity(
    @Id
    @Column(nullable = false, length = 40)
    var id: String?,

    @Column(nullable = false, length = 100)
    var name: String?,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    var password: String?,

    @Column(nullable = false, length = 100)
    var authorities: String?,

    @Column(length = 10)
    var gender: String? = null,

    @Column(length = 20)
    var phone: String? = null,

    @Column(length = 500)
    var description: String? = null
)
