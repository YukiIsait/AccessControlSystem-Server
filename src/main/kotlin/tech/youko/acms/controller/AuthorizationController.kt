package tech.youko.acms.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import tech.youko.acms.helper.JwtHelper

@RestController
@RequestMapping(value = ["/authorization"], method = [RequestMethod.GET])
class AuthorizationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtHelper: JwtHelper
) {
    @RequestMapping(value = ["/login"])
    @PreAuthorize("isAnonymous()")
    fun login(
        @RequestParam("id") id: String,
        @RequestParam("password") password: String
    ): String = jwtHelper.generateToken(
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(id, password)
        )
    )
}
