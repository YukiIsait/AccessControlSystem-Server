package tech.youko.acms.entity

import jakarta.persistence.*

@Entity
@Table(name = "user")
data class UserEntity(
    @Id
    @Column(nullable = false, length = 40)
    var id: String?,

    @Column(nullable = false, length = 100)
    var name: String?,

    @Column(nullable = false, length = 100)
    var password: String?,

    @Column(nullable = false, length = 100)
    var authorities: String?,

    @Column(length = 10)
    var gender: String?,

    @Column(length = 20)
    var phone: String?,

    @Column(length = 100)
    var email: String?,

    @Column(length = 500)
    var description: String?
)
