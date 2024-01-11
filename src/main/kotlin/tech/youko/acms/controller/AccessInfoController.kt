package tech.youko.acms.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tech.youko.acms.entity.AccessInfoEntity
import tech.youko.acms.entity.id.AccessInfoId
import tech.youko.acms.service.IAccessInfoService
import tech.youko.acms.util.commaSeparatedStringToSort

@RestController
@RequestMapping(value = ["/access-info"])
@PreAuthorize("hasRole('ROLE_ADMIN')")
class AccessInfoController(private val accessInfoService: IAccessInfoService) {
    @GetMapping(value = ["/list"])
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

    @PostMapping(value = ["/add"])
    fun add(@RequestBody accessInfoEntity: AccessInfoEntity) = accessInfoService.addAccessInfo(accessInfoEntity)

    @PutMapping(value = ["/update"])
    fun update(@RequestBody accessInfoEntity: AccessInfoEntity) = accessInfoService.updateAccessInfo(accessInfoEntity)

    @DeleteMapping(value = ["/delete"])
    fun delete(
        @RequestParam("device-id") deviceId: String,
        @RequestParam("user-id") userId: String
    ) = accessInfoService.deleteAccessInfoById(AccessInfoId(deviceId, userId))
}
