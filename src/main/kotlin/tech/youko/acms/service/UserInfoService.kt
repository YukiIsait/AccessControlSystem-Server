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
            User(it.id, it.password, AuthorityUtils.commaSeparatedStringToAuthorityList(it.authorities))
        }.orElseThrow {
            UsernameNotFoundException("User '$username' not found")
        }
    }

    override fun getUserById(id: String): UserInfoEntity {
        return userInfoRepository.findById(id).orElseThrow {
            EntityNotFoundException("User '$id' not found")
        }
    }

    override fun listUserWithPage(page: Int, size: Int, sort: Sort): Page<UserInfoEntity> {
        return userInfoRepository.findAll(PageRequest.of(page, size, sort))
    }

    override fun listUserWithPageLike(page: Int, size: Int, sort: Sort, user: UserInfoEntity): Page<UserInfoEntity> {
        val exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        return userInfoRepository.findAll(Example.of(user, exampleMatcher), PageRequest.of(page, size, sort))
    }

    override fun existUserById(id: String): Boolean {
        return userInfoRepository.existsById(id)
    }

    override fun addUser(user: UserInfoEntity) {
        if (userInfoRepository.existsById(user.id)) {
            throw EntityNotFoundException("User '${user.id}' already exists")
        }
        userInfoRepository.save(user.copy(password = passwordEncoder.encode(user.password)))
    }

    override fun deleteUserById(id: String) {
        if (!userInfoRepository.existsById(id)) {
            throw EntityNotFoundException("User '$id' not found")
        }
        userInfoRepository.deleteById(id)
    }

    override fun updateUser(user: UserInfoEntity) {
        val oldUser = userInfoRepository.findById(user.id).orElseThrow {
            EntityNotFoundException("User '${user.id}' not found")
        }
        userInfoRepository.save(
            if (user.password == oldUser.password) {
                user
            } else {
                user.copy(password = passwordEncoder.encode(user.password))
            }
        )
    }
}
