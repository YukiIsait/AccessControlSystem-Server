package tech.youko.acms.controller.management

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tech.youko.acms.entity.UserEntity
import tech.youko.acms.service.IUserService
import tech.youko.acms.util.commaSeparatedStringToSort

@RestController
@RequestMapping("/management/user")
@PreAuthorize("hasRole('ROLE_ADMIN')")
class UserController(private val userService: IUserService) {
    @GetMapping
    fun get(@RequestParam id: String) = userService.getUserById(id)

    @GetMapping("/list")
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "") sort: String,
        userEntity: UserEntity
    ): List<UserEntity> = userService.listUserWithPageLike(
        page,
        size,
        commaSeparatedStringToSort(sort),
        userEntity
    ).content

    @PostMapping("/add")
    fun add(@RequestBody userEntity: UserEntity) = userService.addUser(userEntity)

    @PutMapping("/update")
    fun update(@RequestBody userEntity: UserEntity) = userService.updateUser(userEntity)

    @DeleteMapping("/delete")
    fun delete(@RequestParam id: String) = userService.deleteUserById(id)
}
