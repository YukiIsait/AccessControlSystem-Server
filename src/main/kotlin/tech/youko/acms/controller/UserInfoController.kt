package tech.youko.acms.controller

import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tech.youko.acms.entity.UserInfoEntity
import tech.youko.acms.service.IUserInfoService
import tech.youko.acms.util.commaSeparatedStringToSort

@RestController
@RequestMapping(value = ["/user-info"])
@PreAuthorize("hasRole('ROLE_ADMIN')")
class UserInfoController(private val userInfoService: IUserInfoService) {
    @GetMapping(value = ["/list"])
    fun list(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "sort", defaultValue = "") sort: String
    ): Page<UserInfoEntity> = userInfoService.listUserWithPage(page, size, commaSeparatedStringToSort(sort))

    @PostMapping(value = ["/add"])
    fun add(@RequestBody userInfoEntity: UserInfoEntity) = userInfoService.addUser(userInfoEntity)

    @PutMapping(value = ["/update"])
    fun update(@RequestBody userInfoEntity: UserInfoEntity) = userInfoService.updateUser(userInfoEntity)

    @DeleteMapping(value = ["/delete"])
    fun delete(@RequestParam id: String) = userInfoService.deleteUserById(id)
}
