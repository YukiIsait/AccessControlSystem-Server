package tech.youko.acms.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech.youko.acms.entity.AccessInfoEntity

interface IAccessInfoRepository : JpaRepository<AccessInfoEntity, String>
