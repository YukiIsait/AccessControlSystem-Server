package tech.youko.acms.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import tech.youko.acms.entity.DeviceEntity
import tech.youko.acms.repository.IDeviceRepository

@Service
class DeviceService(private val deviceRepository: IDeviceRepository) : IDeviceService {
    override fun getDeviceById(id: String): DeviceEntity {
        return deviceRepository.findById(id).orElseThrow {
            EntityNotFoundException("Device '$id' not found")
        }
    }

    override fun listDeviceWithPage(page: Int, size: Int, sort: Sort): Page<DeviceEntity> {
        return deviceRepository.findAll(PageRequest.of(page, size, sort))
    }

    override fun listDeviceWithPageLike(page: Int, size: Int, sort: Sort, device: DeviceEntity): Page<DeviceEntity> {
        val exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        return deviceRepository.findAll(Example.of(device, exampleMatcher), PageRequest.of(page, size, sort))
    }

    override fun existDeviceById(id: String): Boolean {
        return deviceRepository.existsById(id)
    }

    override fun addDevice(device: DeviceEntity) {
        if (deviceRepository.existsById(device.id)) {
            throw EntityNotFoundException("Device '${device.id}' already exists")
        }
        deviceRepository.save(device)
    }

    override fun deleteDeviceById(id: String) {
        if (!deviceRepository.existsById(id)) {
            throw EntityNotFoundException("Device '$id' not found")
        }
        deviceRepository.deleteById(id)
    }

    override fun updateDevice(device: DeviceEntity) {
        if (!deviceRepository.existsById(device.id)) {
            throw EntityNotFoundException("Device '${device.id}' not found")
        }
        deviceRepository.save(device)
    }
}
