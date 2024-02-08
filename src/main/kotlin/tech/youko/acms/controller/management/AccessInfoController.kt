package tech.youko.acms.controller.management

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tech.youko.acms.entity.AccessInfoEntity
import tech.youko.acms.entity.id.AccessInfoId
import tech.youko.acms.service.IAccessInfoService
import tech.youko.acms.util.commaSeparatedStringToSort

@RestController
@RequestMapping("/management/access-info")
@PreAuthorize("hasRole('ROLE_ADMIN')")
class AccessInfoController(private val accessInfoService: IAccessInfoService) {
    @GetMapping("/get")
    fun get(
        @RequestParam("device-id") deviceId: String,
        @RequestParam("user-id") userId: String
    ) = accessInfoService.getAccessInfoById(AccessInfoId(deviceId, userId))

    @GetMapping("/list")
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "") sort: String,
        accessInfoEntity: AccessInfoEntity
    ): List<AccessInfoEntity> = accessInfoService.listAccessInfoWithPageLike(
        page,
        size,
        commaSeparatedStringToSort(sort),
        accessInfoEntity
    ).content

    @PostMapping("/add")
    fun add(@RequestBody accessInfoEntity: AccessInfoEntity) = accessInfoService.addAccessInfo(accessInfoEntity)

    @PutMapping("/update")
    fun update(@RequestBody accessInfoEntity: AccessInfoEntity) = accessInfoService.updateAccessInfo(accessInfoEntity)

    @DeleteMapping("/delete")
    fun delete(
        @RequestParam("device-id") deviceId: String,
        @RequestParam("user-id") userId: String
    ) = accessInfoService.deleteAccessInfoById(AccessInfoId(deviceId, userId))
}
