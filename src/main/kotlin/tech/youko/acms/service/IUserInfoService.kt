package tech.youko.acms.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.security.core.userdetails.UserDetailsService
import tech.youko.acms.entity.UserInfoEntity

interface IUserInfoService : UserDetailsService {
    fun getUserById(id: String): UserInfoEntity

    fun getUserWithPage(
        page: Int,
        size: Int,
        sort: Sort = Sort.unsorted()
    ): Page<UserInfoEntity>

    fun getUserWithPageLike(
        page: Int,
        size: Int,
        sort: Sort = Sort.unsorted(),
        user: UserInfoEntity
    ): Page<UserInfoEntity>

    fun existUserById(id: String): Boolean

    fun addUser(user: UserInfoEntity)

    fun deleteUserById(id: String)

    fun updateUser(user: UserInfoEntity)
}
