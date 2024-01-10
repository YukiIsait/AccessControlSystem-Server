package tech.youko.acms.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech.youko.acms.entity.DeviceEntity

interface IDeviceRepository : JpaRepository<DeviceEntity, String>
