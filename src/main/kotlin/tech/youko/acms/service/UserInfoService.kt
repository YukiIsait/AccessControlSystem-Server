package tech.youko.acms.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.*
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tech.youko.acms.entity.UserInfoEntity
import tech.youko.acms.repository.IUserInfoRepository

@Service
class UserInfoService(
    private val userInfoRepository: IUserInfoRepository,
    private val passwordEncoder: PasswordEncoder
) : IUserInfoService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userInfoRepository.findById(username).map {
            User(it.id, it.password, AuthorityUtils.commaSeparatedStringToAuthorityList(it.roles))
        }.orElseThrow {
            UsernameNotFoundException("User '$username' not found")
        }
    }

    override fun getUserById(id: String): UserInfoEntity {
        return userInfoRepository.findById(id).orElseThrow {
            EntityNotFoundException("User '$id' not found")
        }
    }

    override fun getUserWithPage(page: Int, size: Int, sort: Sort): Page<UserInfoEntity> {
        return userInfoRepository.findAll(PageRequest.of(page, size, sort))
    }

    override fun getUserWithPageLike(page: Int, size: Int, sort: Sort, user: UserInfoEntity): Page<UserInfoEntity> {
        val exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        return userInfoRepository.findAll(Example.of(user, exampleMatcher), PageRequest.of(page, size, sort))
    }

    override fun existUserById(id: String): Boolean {
        return userInfoRepository.existsById(id)
    }

    override fun addUser(user: UserInfoEntity) {
        userInfoRepository.save(user.copy(password = passwordEncoder.encode(user.password)))
    }

    override fun deleteUserById(id: String) {
        userInfoRepository.deleteById(id)
    }

    override fun updateUser(user: UserInfoEntity) {
    }
}
