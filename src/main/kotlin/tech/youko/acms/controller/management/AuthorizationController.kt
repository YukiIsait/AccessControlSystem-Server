package tech.youko.acms.controller.management

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import tech.youko.acms.helper.JwtHelper

@RestController
@RequestMapping(value = ["/management/authorization"])
@PreAuthorize("isAnonymous()")
class AuthorizationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtHelper: JwtHelper
) {
    @GetMapping(value = ["/login"])
    fun login(
        @RequestParam id: String,
        @RequestParam password: String
    ): String = jwtHelper.generateToken(
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(id, password)
        )
    )
}
