package tech.youko.acms.controller.management

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import tech.youko.acms.entity.UserEntity
import tech.youko.acms.helper.JwtHelper
import tech.youko.acms.service.IUserService
import tech.youko.acms.structure.response.LoginStructure

@RestController
@RequestMapping("/management/authorization")
class AuthorizationController(
    private val authenticationManager: AuthenticationManager,
    private val userService: IUserService,
    private val jwtHelper: JwtHelper
) {
    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")
    fun get(): UserEntity = userService.getUserById(SecurityContextHolder.getContext().authentication.name)

    @GetMapping("/validate")
    fun validate(
        @RequestParam token: String
    ): Boolean = jwtHelper.validateToken(token)

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    fun login(
        @RequestParam id: String,
        @RequestParam password: String
    ): LoginStructure = LoginStructure(
        jwtHelper.generateToken(
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(id, password)
            )
        ),
        userService.getUserById(id)
    )
}
