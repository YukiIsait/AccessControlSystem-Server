package tech.youko.acms.service

import org.springframework.security.core.Authentication

interface IJwtTokenService {
    /**
     * 基于用户信息生成 Token
     * @param authentication 用户信息
     * @return Token
     */
    fun generateToken(authentication: Authentication): String

    /**
     * 从 Token 中解析出用户信息
     * @param token Token
     * @return 用户信息
     */
    fun getAuthentication(token: String): Authentication

    /**
     * 验证 Token 是否合法
     * @param token Token
     * @return 当 Token 解析出用户信息且用户信息未过期时返回 true, 否则返回 false
     */
    fun validateToken(token: String): Boolean
}
