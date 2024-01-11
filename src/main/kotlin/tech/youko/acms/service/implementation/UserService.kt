package tech.youko.acms.service.implementation

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.*
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tech.youko.acms.entity.UserEntity
import tech.youko.acms.repository.IUserRepository
import tech.youko.acms.service.IUserService

@Service
class UserService(
    private val userRepository: IUserRepository,
    private val passwordEncoder: PasswordEncoder
) : IUserService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findById(username).map {
            User(it.id, it.password, AuthorityUtils.commaSeparatedStringToAuthorityList(it.authorities))
        }.orElseThrow {
            UsernameNotFoundException("User '$username' not found")
        }
    }

    override fun getUserById(id: String): UserEntity {
        return userRepository.findById(id).orElseThrow {
            EntityNotFoundException("User '$id' not found")
        }
    }

    override fun listUserWithPage(page: Int, size: Int, sort: Sort): Page<UserEntity> {
        return userRepository.findAll(PageRequest.of(page, size, sort))
    }

    override fun listUserWithPageLike(page: Int, size: Int, sort: Sort, user: UserEntity): Page<UserEntity> {
        val exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        return userRepository.findAll(Example.of(user, exampleMatcher), PageRequest.of(page, size, sort))
    }

    override fun existUserById(id: String): Boolean {
        return userRepository.existsById(id)
    }

    override fun addUser(user: UserEntity) {
        if (userRepository.existsById(user.id!!)) {
            throw EntityNotFoundException("User '${user.id}' already exists")
        }
        userRepository.save(user.copy(password = passwordEncoder.encode(user.password)))
    }

    override fun deleteUserById(id: String) {
        if (!userRepository.existsById(id)) {
            throw EntityNotFoundException("User '$id' not found")
        }
        userRepository.deleteById(id)
    }

    override fun updateUser(user: UserEntity) {
        val oldUser = userRepository.findById(user.id!!).orElseThrow {
            EntityNotFoundException("User '${user.id}' not found")
        }
        userRepository.save(
            if (user.password == oldUser.password) {
                user
            } else {
                user.copy(password = passwordEncoder.encode(user.password))
            }
        )
    }
}
