package tech.youko.acms.controller.management

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tech.youko.acms.entity.DeviceEntity
import tech.youko.acms.service.IDeviceService
import tech.youko.acms.structure.response.PageStructure
import tech.youko.acms.util.commaSeparatedStringToSort

@RestController
@RequestMapping("/management/device")
@PreAuthorize("hasRole('ROLE_ADMIN')")
class DeviceController(private val deviceService: IDeviceService) {
    @GetMapping
    fun get(@RequestParam id: String): DeviceEntity = deviceService.getDeviceById(id)

    @GetMapping("/list")
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "") sort: String,
        deviceEntity: DeviceEntity
    ): PageStructure<DeviceEntity> = PageStructure.fromPage(
        deviceService.listDeviceWithPageLike(
            page,
            size,
            commaSeparatedStringToSort(sort),
            deviceEntity
        )
    )

    @PostMapping("/add")
    fun add(@RequestBody deviceEntity: DeviceEntity) = deviceService.addDevice(deviceEntity)

    @PutMapping("/update")
    fun update(@RequestBody deviceEntity: DeviceEntity) = deviceService.updateDevice(deviceEntity)

    @DeleteMapping("/delete")
    fun delete(@RequestParam id: String) = deviceService.deleteDeviceById(id)
}
