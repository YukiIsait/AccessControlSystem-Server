package tech.youko.acms.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.security.core.userdetails.UserDetailsService
import tech.youko.acms.entity.UserEntity

interface IUserService : UserDetailsService {
    fun getUserById(id: String): UserEntity

    fun listUserWithPage(
        page: Int,
        size: Int,
        sort: Sort = Sort.unsorted()
    ): Page<UserEntity>

    fun listUserWithPageLike(
        page: Int,
        size: Int,
        sort: Sort = Sort.unsorted(),
        user: UserEntity
    ): Page<UserEntity>

    fun existUserById(id: String): Boolean

    fun addUser(user: UserEntity)

    fun deleteUserById(id: String)

    fun updateUser(user: UserEntity)
}
