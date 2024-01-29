package tech.youko.acms.controller.device

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/device/accessibility"])
@PreAuthorize("permitAll()")
class AccessibilityController {
    @GetMapping
    fun accessibility(): Boolean {
        return true
    }
}
