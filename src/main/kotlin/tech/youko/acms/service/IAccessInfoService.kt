package tech.youko.acms.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import tech.youko.acms.entity.AccessInfoEntity

interface IAccessInfoService {
    fun getAccessInfoById(id: String): AccessInfoEntity

    fun listAccessInfoWithPage(
        page: Int,
        size: Int,
        sort: Sort = Sort.unsorted()
    ): Page<AccessInfoEntity>

    fun listAccessInfoWithPageLike(
        page: Int,
        size: Int,
        sort: Sort = Sort.unsorted(),
        accessInfo: AccessInfoEntity
    ): Page<AccessInfoEntity>

    fun existAccessInfoById(id: String): Boolean

    fun addAccessInfo(accessInfo: AccessInfoEntity)

    fun deleteAccessInfoById(id: String)

    fun updateAccessInfo(accessInfo: AccessInfoEntity)
}
