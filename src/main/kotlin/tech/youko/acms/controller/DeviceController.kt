package tech.youko.acms.controller

import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tech.youko.acms.entity.DeviceEntity
import tech.youko.acms.service.IDeviceService
import tech.youko.acms.util.commaSeparatedStringToSort

@RestController
@RequestMapping(value = ["/device"])
@PreAuthorize("hasRole('ROLE_ADMIN')")
class DeviceController(private val deviceService: IDeviceService) {
    @GetMapping(value = ["/list"])
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "") sort: String,
        deviceEntity: DeviceEntity
    ): List<DeviceEntity> = deviceService.listDeviceWithPageLike(
        page,
        size,
        commaSeparatedStringToSort(sort),
        deviceEntity
    ).content

    @PostMapping(value = ["/add"])
    fun add(@RequestBody deviceEntity: DeviceEntity) = deviceService.addDevice(deviceEntity)

    @PutMapping(value = ["/update"])
    fun update(@RequestBody deviceEntity: DeviceEntity) = deviceService.updateDevice(deviceEntity)

    @DeleteMapping(value = ["/delete"])
    fun delete(@RequestParam id: String) = deviceService.deleteDeviceById(id)
}
