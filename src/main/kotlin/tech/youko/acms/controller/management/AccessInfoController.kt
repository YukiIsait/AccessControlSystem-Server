package tech.youko.acms.controller.management

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tech.youko.acms.entity.AccessInfoEntity
import tech.youko.acms.entity.id.AccessInfoId
import tech.youko.acms.service.IAccessInfoService
import tech.youko.acms.structure.response.PageStructure
import tech.youko.acms.util.commaSeparatedStringToSort

@RestController
@RequestMapping("/management/accessInfo")
@PreAuthorize("hasRole('ROLE_ADMIN')")
class AccessInfoController(private val accessInfoService: IAccessInfoService) {
    @GetMapping("/get")
    fun get(
        @RequestParam deviceId: String,
        @RequestParam userId: String
    ): AccessInfoEntity = accessInfoService.getAccessInfoById(AccessInfoId(deviceId, userId))

    @GetMapping("/list")
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "") sort: String,
        accessInfoEntity: AccessInfoEntity
    ): PageStructure<AccessInfoEntity> = PageStructure.fromPage(
        accessInfoService.listAccessInfoWithPageLike(
            page,
            size,
            commaSeparatedStringToSort(sort),
            accessInfoEntity
        )
    )

    @PostMapping("/add")
    fun add(
        @RequestBody accessInfoEntity: AccessInfoEntity
    ) = accessInfoService.addAccessInfo(accessInfoEntity)

    @PutMapping("/update")
    fun update(
        @RequestBody accessInfoEntity: AccessInfoEntity
    ) = accessInfoService.updateAccessInfo(accessInfoEntity)

    @DeleteMapping("/delete")
    fun delete(
        @RequestParam deviceId: String,
        @RequestParam userId: String
    ) = accessInfoService.deleteAccessInfoById(AccessInfoId(deviceId, userId))
}
