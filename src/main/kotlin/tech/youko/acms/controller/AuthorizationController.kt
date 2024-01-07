package tech.youko.acms.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tech.youko.acms.service.JwtTokenService

@RestController
@RequestMapping(value = ["/authorization"])
class AuthorizationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenService: JwtTokenService
) {
    @GetMapping(value = ["/login"])
    @PreAuthorize("isAnonymous()")
    fun login(
        @RequestParam("id") id: String,
        @RequestParam("password") password: String
    ): String = jwtTokenService.generateToken(
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(id, password)
        )
    )
}
