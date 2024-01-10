package tech.youko.acms.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import tech.youko.acms.entity.AccessInfoEntity

interface IAccessInfoService {
    fun getAccessById(id: String): AccessInfoEntity

    fun listAccessWithPage(
        page: Int,
        size: Int,
        sort: Sort = Sort.unsorted()
    ): Page<AccessInfoEntity>

    fun listAccessWithPageLike(
        page: Int,
        size: Int,
        sort: Sort = Sort.unsorted(),
        access: AccessInfoEntity
    ): Page<AccessInfoEntity>

    fun existAccessById(id: String): Boolean

    fun addAccess(access: AccessInfoEntity)

    fun deleteAccessById(id: String)

    fun updateAccess(access: AccessInfoEntity)
}
