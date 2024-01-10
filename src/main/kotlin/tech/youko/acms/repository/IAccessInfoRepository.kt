package tech.youko.acms.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech.youko.acms.entity.AccessInfoEntity
import tech.youko.acms.entity.id.AccessInfoId

interface IAccessInfoRepository : JpaRepository<AccessInfoEntity, AccessInfoId>
