package tech.youko.acms.controller.device

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tech.youko.acms.entity.id.AccessInfoId
import tech.youko.acms.service.IAccessInfoService

@RestController
@RequestMapping("/device/accessibility")
@PreAuthorize("permitAll()")
class AccessibilityController(private val accessInfoService: IAccessInfoService) {
    @GetMapping("/access")
    @PreAuthorize("permitAll()")
    fun access(
        @RequestParam("device-id") deviceId: String,
        @RequestParam("user-id") userId: String
    ): Boolean = accessInfoService.existAccessInfoById(AccessInfoId(deviceId, userId))
}
