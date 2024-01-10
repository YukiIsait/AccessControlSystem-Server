package tech.youko.acms.controller

import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tech.youko.acms.entity.UserEntity
import tech.youko.acms.service.IUserService
import tech.youko.acms.util.commaSeparatedStringToSort

@RestController
@RequestMapping(value = ["/user"])
@PreAuthorize("hasRole('ROLE_ADMIN')")
class UserController(private val userService: IUserService) {
    @GetMapping(value = ["/list"])
    fun list(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "sort", defaultValue = "") sort: String
    ): Page<UserEntity> = userService.listUserWithPage(page, size, commaSeparatedStringToSort(sort))

    @PostMapping(value = ["/add"])
    fun add(@RequestBody userEntity: UserEntity) = userService.addUser(userEntity)

    @PutMapping(value = ["/update"])
    fun update(@RequestBody userEntity: UserEntity) = userService.updateUser(userEntity)

    @DeleteMapping(value = ["/delete"])
    fun delete(@RequestParam id: String) = userService.deleteUserById(id)
}
