package tech.youko.acms.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import tech.youko.acms.entity.DeviceEntity

interface IDeviceService {
    fun getDeviceById(id: String): DeviceEntity

    fun listDeviceWithPage(
        page: Int,
        size: Int,
        sort: Sort = Sort.unsorted()
    ): Page<DeviceEntity>

    fun listDeviceWithPageLike(
        page: Int,
        size: Int,
        sort: Sort = Sort.unsorted(),
        device: DeviceEntity
    ): Page<DeviceEntity>

    fun existDeviceById(id: String): Boolean

    fun addDevice(device: DeviceEntity)

    fun deleteDeviceById(id: String)

    fun updateDevice(device: DeviceEntity)
}
